
s=int(input("enter the size   "))
hash=[None]*s
def Lin_Probe():
    
    k=int(input("enter the phone number  "))


    index = k % s
    if hash[index] == None :
        hash[index] = k
    else:
        for i in range(s):
            index=(k+i)%s
            if hash[index] == None:
                hash[index] = k
                return hash
            else:
                print("table full")
    print(hash)
def Quad_Probe():
    k=int(input("Enter the phone number : "))
    index = k % s
    
    if hash[index] == None :
        hash[index] = k
    else:
        for i in range(1,s):
                index = (k +i*i)%s
                if hash[index] == None:
                    hash[index] = k 
                    return hash
                else:
                    print("table is full")

    print(hash)
def Search():
    count = 1
    for i in range(s):
        key = int(input("Enter the phone number to be searched"))
        index = (key + i) % s
        if hash[index] == key:
            print("phone number found at posotion",index,"\n")
            print("total comparisions",count)
        elif hash[index] == None:
            print("phone number not found")
        else:
            count+=1
            
            


while True:
    ch=input("Enter the choice 1.Linear_probe 2.Quadratic_Probe 3.Searching  4.Exit")
    if ch=='1':
        n=int(input("enter the number of phone numbers to be entered"))
        for i in range(n):
            Lin_Probe()
    elif ch=='2':
        n1=int(input("enter the number of phone numbers to be entered"))
        for i in range(n1):
            Quad_Probe
    elif ch=='3':
        Search()
    elif ch=='4':
        break
    else:
        print("invalid input ")






        

    



