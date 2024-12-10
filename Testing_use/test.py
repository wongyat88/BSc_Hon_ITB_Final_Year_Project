import json

def array_merge( first_array , second_array ):
    if isinstance( first_array , list ) and isinstance( second_array , list ):
            return first_array + second_array
    elif isinstance( first_array , dict ) and isinstance( second_array , dict ):
            return dict( list( first_array.items() ) + list( second_array.items() ) )
    elif isinstance( first_array , set ) and isinstance( second_array , set ):
            return first_array.union( second_array )
    return False
    
dic={1:{'name':'apple','pid':1,'number':3,'price':5}}
dic2={2:{'name':'orange','pid':2,'number':1,'price':1}}
dic3={'qwe':{'name':'orange','pid':2,'number':1,'price':1}}

dic=array_merge(dic, dic2)
dic=array_merge(dic, dic3)

print("old:"+"\n")
for key, value in dic.items():
    if key == 1:
        print(dic[key]['name'])
        print(dic[key]['pid'])
        print(dic[key]['number'])
        print(dic[key]['price'])
    elif key == 2:
        print(dic[key]['name'])
        print(dic[key]['pid'])
        print(dic[key]['number'])
        print(dic[key]['price'])

print("\n")

for key, value in dic.items():
    if key == 1:
        dic[key]['name']='on9apple'
        dic[key]['number']=10
        dic[key]['price']='qwe'
    elif key == 2:
        dic[key]['name']=''

print("new:"+"\n")
for key, value in dic.items():
    if key == 1:
        print(dic[key]['name'])
        print(dic[key]['pid'])
        print(dic[key]['number'])
        print(dic[key]['price'])
    elif key == 2:
        print(dic[key]['name'])
        print(dic[key]['pid'])
        print(dic[key]['number'])
        print(dic[key]['price'])

print(dic)
dic.pop('qwe', None)
print(dic)

with open("my.json","w") as f:
    json.dump(dic,f)

with open("my.json") as test:
    dictionary = json.load(test)
print("new\n")
print(dictionary)
print(dictionary["1"])
print(dictionary["1"]["name"])
