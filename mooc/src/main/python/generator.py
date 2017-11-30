import random
import time

ip_slices=[132,156,124,10,29,167,143,187,30,100]
status_code =[404,302,200]
#ip
def sample_ip():
    slice = random.sample(ip_slices,4)
    return ".".join([str(item) for item in slice])

#k开发状态码
def sample_status_code():
    return random.sample(status_code,1)[0]

def geneate_log(count = 10):
    time_str=time.strftime("%Y-%m-%d %H:%M:%S",time.localtime)
    f=open("/root/mike/data/access.log","w+")
    while count >= 1:
        query_log = "{ip}\t{local_time}\t\"GET /{url} HTTP/1.1\"\t{stats_code}\t{referer}".format(ip=sample_ip(),stats1=sample_status())
        print query_log
        f.write(query_log +"\n")
        count = count -1

if __name__ == '__main__':
    geneate_log(100)

	
