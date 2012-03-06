#include <stdio.h>
#include <netinet/in.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/select.h>
#include <sys/time.h>
#include <errno.h>
#include <string.h>
#include <signal.h>


#define PORT 8080

void chomp(char *st) {
	char* s =st;
	while(*s && *s != '\n' && *s != '\r') s++;

	*s = 0;
//return st;	
}



struct element {
	in_addr_t addr;
	char* smg;
	int reqd;
	int alarm;
	int ready;
	int dead;
	struct element *next;
};

struct element *head = NULL;

int push(in_addr_t newaddr,char* smg) {
	struct element *cur;
	int flag = 1;
	for(cur = head; cur != NULL; cur = cur->next) {
		if(cur->addr == newaddr){
			strcpy(cur->smg,smg);
			chomp(cur->smg);
			if(cur->dead==1){
				cur->dead=0;
				return 0;
			}
			return 1-(cur->reqd);
		}
	}
	struct element *new_element = (struct element*)malloc(sizeof(struct element));
	new_element->next = head;
	new_element->addr = newaddr;
	new_element->smg=(char*)malloc(1024*sizeof(char));
	new_element->ready=0;
	new_element->alarm=1;
	new_element->dead=0;
	chomp(new_element->smg);
	strcpy(new_element->smg,smg);
	head = new_element;
	return 0;
}

void make_reqd(in_addr_t newaddr){
	struct element *cur;
	for(cur = head; cur != NULL; cur = cur->next) {
		if(cur->addr == newaddr) {
			cur->reqd = 1;
		}
	}
}

void not_reqd(in_addr_t newaddr){
	struct element *cur;
	for(cur = head; cur != NULL; cur = cur->next) {
		if(cur->addr == newaddr) {
			cur->reqd = 0;
		}
	}
}

void make_ready(in_addr_t newaddr){
	struct element *cur;
	for(cur = head; cur != NULL; cur = cur->next) {
		if(cur->addr == newaddr) {
			cur->ready = 1;
		}
	}
}

void set_alarm(in_addr_t newaddr){
	struct element *cur;
	for(cur = head; cur != NULL; cur = cur->next) {
		if(cur->addr == newaddr) {
			cur->alarm = 0;
		}
	}
}

void killa(in_addr_t newaddr){
	struct element *cur;
	for(cur = head; cur != NULL; cur = cur->next) {
		if(cur->addr == newaddr) {
			cur->dead = 1;
		}
	}
}


void rem_alarm(in_addr_t newaddr){
	struct element *cur;
	for(cur = head; cur != NULL; cur = cur->next) {
		if(cur->addr == newaddr) {
			cur->alarm = 1;
		}
	}
}

char* search(in_addr_t newaddr) {
	struct element *cur;
	for(cur = head; cur != NULL; cur = cur->next) {
		if(cur->addr == newaddr) {
			return cur->smg;
		}
	}
	return NULL;
}

int valid(in_addr_t newaddr) {
	struct element *cur;
	for(cur = head; cur != NULL; cur = cur->next) {
		if(cur->addr == newaddr && cur->dead==0) {
			return 1;
		}
	}
	return 0;
}


int noreqd(){
	struct element *cur;
	int sum=0;
	for(cur = head; cur != NULL; cur = cur->next) {
		sum = sum + cur->reqd;
	}
	return sum;
}






int main(int argc,char* argv[]) {
	
	int recvfd = socket(AF_INET, SOCK_DGRAM, 0);
	int sendfd = socket(AF_INET, SOCK_DGRAM, 0);
	int bcastfd = socket(AF_INET, SOCK_DGRAM, 0);
	int on = 1;
	/*if(setsockopt(bcastfd, SOL_SOCKET, SO_BROADCAST, &on, sizeof(on)) < 0) {
	perror("setsockopt");
	return -1;
	}*/
	int i = 0;

	struct sockaddr_in my_addr;
	my_addr.sin_family = PF_INET;
	my_addr.sin_port = htons(PORT);
	my_addr.sin_addr.s_addr = INADDR_ANY;
	memset( my_addr.sin_zero, '\0', sizeof(my_addr.sin_zero) );

	int bind_ret = bind(recvfd, (struct sockaddr*)&my_addr, sizeof(struct sockaddr));
	if(bind_ret != 0) {
		perror("recv bind");
		return -1;
	}
	my_addr.sin_port = htons(PORT+1);
	bind_ret = bind(sendfd, (struct sockaddr*)&my_addr, sizeof(struct sockaddr));
	if(bind_ret != 0) {
		perror("send bind");
		return -1;
	}
	my_addr.sin_port = htons(PORT+2);
	my_addr.sin_addr.s_addr = INADDR_BROADCAST;
	bind_ret = bind(bcastfd, (struct sockaddr*)&my_addr, sizeof(struct sockaddr));
	if(bind_ret != 0) {
		perror("bcast bind");
		return -1;
	}

	fd_set readfds;
	FD_ZERO(&readfds);

	char str[1024];

	struct sockaddr_in recv_addr;
	socklen_t size = sizeof(struct sockaddr);

	FILE* ff = fopen(argv[1],"r");

	int total_hosts;

	fscanf(ff,"%d\n",&total_hosts);


	for(i=0;i<total_hosts;i++){
		char* host;
		host=(char*)malloc(1024*sizeof(char));
		fgets(host,1023,ff);
		push(inet_addr(host),"");
		make_reqd(inet_addr(host));
	}
	char* smg;

	smg=(char*)malloc(1023*sizeof(char));

	fgets(smg,1023,ff);
	chomp(smg);

	int allreq;
	fscanf(ff,"%d\n",&allreq);

	struct sockaddr_in dest_addr;
	dest_addr.sin_family = PF_INET;
	dest_addr.sin_port = htons(PORT);
	memset( dest_addr.sin_zero, '\0', sizeof(dest_addr.sin_zero) );
	struct element *curelement;

	char* reqchar;
	for(curelement = head; curelement != NULL; curelement = curelement->next) {
		dest_addr.sin_addr.s_addr = curelement->addr;
		sendto(sendfd, str, strlen(str), 0, (struct sockaddr*)&dest_addr, sizeof(dest_addr));

		reqchar=(char*)malloc(1024*sizeof(char));
		sprintf(reqchar,"r%s",smg);
		int send_stat = sendto(bcastfd, reqchar, strlen(reqchar), 0, (struct sockaddr*)&dest_addr, sizeof(struct sockaddr));
		if(send_stat < 0) {
			perror("bcast");
			return -1;
		}
	}

	int count=0;

	while(1) {

		FD_SET(0, &readfds);
		FD_SET(recvfd, &readfds);
		select(recvfd+1, &readfds, NULL, NULL, NULL);
		
		if(FD_ISSET(0, &readfds)) { /* came from stdin */
			count++;
			if(count>5){
				for(curelement = head; curelement != NULL; curelement = curelement->next) {
					reqchar=(char*)malloc(1024*sizeof(char));
					sprintf(reqchar,"r%s",smg);
					dest_addr.sin_addr.s_addr = curelement->addr;
					sendto(sendfd, reqchar, strlen(reqchar), 0, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
				}
				count=0;				
			}
			memset(str, 0, 1024);
			str[0] = 'd';
			fgets(&str[1], 1023, stdin);
			/* TODO: send */
			if(str[1]=='%') {
				str[0]='s';
				strcpy(smg,&str[2]);
			}
			if(str[1]=='!'){
				for(curelement = head; curelement != NULL; curelement = curelement->next) {
					reqchar=(char*)malloc(1024*sizeof(char));
					sprintf(reqchar,"r%s",smg);
					dest_addr.sin_addr.s_addr = curelement->addr;
					sendto(sendfd, reqchar, strlen(reqchar), 0, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
				}
		
			}
			else if(noreqd()==0 || allreq==0){
				for(curelement = head; curelement != NULL; curelement = curelement->next) {
				//				printf("[+] Sending\n");
					if((curelement->ready!=0 || allreq==0) && (curelement->dead==0)){
						dest_addr.sin_addr.s_addr = curelement->addr;
						if(curelement->alarm == 0){
							printf("%s is mostly dead.\n",inet_ntoa(dest_addr.sin_addr));
							killa(curelement->addr);
						}
						else{
							sendto(sendfd, str, strlen(str), 0, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
						}
						set_alarm(curelement->addr);
					}
				}
			}
			else{
				printf("Cannot talk till connected to all\n");

			}
		}

		if(FD_ISSET(recvfd, &readfds)) {
		//			printf("[+] Received on socket\n");
			memset(str, 0, 1024);
			recvfrom(recvfd, str, 1023, 0, (struct sockaddr*)&recv_addr, &size);
			if(str[0] == 'r') {
				reqchar=(char*)malloc(1024*sizeof(char));
				sprintf(reqchar,"a%s",smg);
				dest_addr.sin_addr.s_addr = recv_addr.sin_addr.s_addr;
				sendto(sendfd, reqchar, strlen(reqchar), 0, (struct sockaddr*)&dest_addr, sizeof(struct sockaddr));

				if(push(recv_addr.sin_addr.s_addr,&str[1]) == 0) {
					printf("\n [+] new client %s\n", inet_ntoa(recv_addr.sin_addr));
					not_reqd(recv_addr.sin_addr.s_addr);
					if(noreqd()==0 && allreq!=0){
						char ch ='n';
						/* TODO: send */
						for(curelement = head; curelement != NULL; curelement = curelement->next) {
							//				printf("[+] Sending\n");
							dest_addr.sin_addr.s_addr = curelement->addr;
							sendto(sendfd, &ch, 1, 0, (struct sockaddr*)&dest_addr, sizeof(dest_addr));

						}
					}
				}
			}
			else if(str[0] == 'a') {
				if(push(recv_addr.sin_addr.s_addr,&str[1]) == 0) {
					printf("\n [+] new client %s\n", inet_ntoa(recv_addr.sin_addr));
					not_reqd(recv_addr.sin_addr.s_addr);
					if(noreqd()==0 && allreq!=0){
						char ch ='n';
						/* TODO: send */
						for(curelement = head; curelement != NULL; curelement = curelement->next) {
							//				printf("[+] Sending\n");
							dest_addr.sin_addr.s_addr = curelement->addr;
							sendto(sendfd, &ch, 1, 0, (struct sockaddr*)&dest_addr, sizeof(dest_addr));

						}
					}
				}
			}
			else if(str[0] == 's'){
				push(recv_addr.sin_addr.s_addr,&str[2]);
				printf("%s has changed status message to %s \n",inet_ntoa(recv_addr.sin_addr),&str[2]);
				char ch ='x';
				dest_addr.sin_addr.s_addr = recv_addr.sin_addr.s_addr ;
				sendto(sendfd, &ch, 1, 0, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
			}
			else if(str[0] == 'x'){
				rem_alarm(recv_addr.sin_addr.s_addr);
			}
			else if(str[0] == 'n' && allreq!=0){
				make_ready(recv_addr.sin_addr.s_addr);
				printf("%s is ready \n",inet_ntoa(recv_addr.sin_addr));

			}
			else if ((noreqd() == 0 || allreq==0) && str[0]=='d' && valid(recv_addr.sin_addr.s_addr)==1){	
				printf("\n %s [%s] : %s \n", inet_ntoa(recv_addr.sin_addr),search(recv_addr.sin_addr.s_addr), &str[1]);
				char ch ='x';
				dest_addr.sin_addr.s_addr = recv_addr.sin_addr.s_addr ;
				sendto(sendfd, &ch, 1, 0, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
			}
		}
	}

	return 0;
}
