#!/bin/bash

GREEN='tput setaf 2'
YELLOW='tput setaf 3'


echo "witch command do you want to do"
echo "$(tput setaf 1)1) $(tput setaf 3) 10 popular ip"
echo "$(tput setaf 3)2) $(tput setaf 2) special URL"
echo "$(tput setaf 4)3) $(tput setaf 6) most used addresses"
echo "$(tput setaf 5)4) $(tput setaf 4) most used browsers"
echo "$(tput setaf 6)5) $(tput setaf 5) most used operating systems$(tput setaf 7)"


read order
if [[ $order == 1 ]]; then
	cat $1|awk '{print $1}'|sort |uniq -c | sort -nr | awk '{print $1" times in this ip:"$2}' | head -n 10 |less	

elif [[ $order == 2 ]]; then
	echo "please type your URL like: $(tput setaf 1)www.google.com"
	read URL_VAL	
	cat $1 | awk -F '"' '{print $4}'| awk -F / '{print $3}'| sort|uniq -c | sort -nr | grep $URL_VAL|less



elif [[ $order == 3 ]]; then
	cat $1 | awk -F '"' '{print $4}'| awk -F / '{print $3}'| sort|uniq -c | sort -nr | less


elif [[ $order == 4 ]]; then
	cat $1 |cut -d '"' -f 6| awk '{print $1,$(NF-1),$NF}'|awk -F / '{print $1,$2,$3}'|sort |uniq -c | sort -nr |less	

else
	cat $1 |cut -d '"' -f 6| cut -d '(' -f 2|awk '{print $1}'|sort |uniq -c | sort -nr |less
fi
