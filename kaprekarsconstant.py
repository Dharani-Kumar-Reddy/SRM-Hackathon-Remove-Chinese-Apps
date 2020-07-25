def kaprekar(d,num,s):
    if(num==0):
        return 0
    digits=[0]*d
    asc,des=0,0
    for i in range(d):
        digits[i]=num%10
        num=(num//10)
    digits.sort()
    for i in range(d):
        asc=(asc*10)+digits[i]
    for i in range(d-1,-1,-1):
        des=des*10+digits[i]
    difference=abs(asc-des)
    if(difference in s):
        return difference
    s.add(difference)
    return kaprekar(d,difference,s)

def kaprekar_series(d,num,s):
    if(num==0):
        return 0
    digits=[0]*d
    asc,des=0,0
    for i in range(d):
        digits[i]=num%10
        num=(num//10)
    digits.sort()
    for i in range(d):
        asc=(asc*10)+digits[i]
    for i in range(d-1,-1,-1):
        des=des*10+digits[i]
    difference=abs(asc-des)
    if(difference in s):
        return s
    s.append(difference)
    return kaprekar_series(d,difference,s)
dig=int(input("Enter number of digits : "))
num=int(input("Enter any  number(Do not enter nos with all same digits like 111) : "))
s=set()
n=kaprekar(dig,num,s)
print("kaprekar constant is:",n)
k=list()
k.append(n)
ans=kaprekar_series(dig,n,k)
if len(ans)>1:
    print("Kaprekar series is - ")
    print(ans)



