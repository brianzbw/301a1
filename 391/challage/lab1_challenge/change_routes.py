import sqlite3

infile = open('routes.dat', 'r')
outfile = open('newRoutes.dat','w')

done = 0
 
while not  done:  
        aLine = infile.readline()  
        if(aLine != ''):  
            a=aLine.split(',')
            b = a[8].split()
            if (len(b) == 1 or len(b) == 0):
          	s=','.join(list(a[:1]) + list(a[2:3]) + a[5:])
            	outfile.write(s)
            	
            else:
            	t=len(b)
   
            	while t > 0:
            		
   			a[8] = b[t-1]+'\r\n'
            		s=','.join(list(a[1:2]) + list(a[3:4]) + a[5:])
            		outfile.write(s)
            		t = t-1
      
       
        else:  
            done = 1  



outfile.close()
infile.close()


