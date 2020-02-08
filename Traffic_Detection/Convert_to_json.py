import json

f = open('record.csv',"r")
#the columns are on the first line separated by ;
columns = f.readline().split(";");
objs = {}
rows = f.readlines();
for row in rows:
    fields = row.split(";")
    obj = {}
    for column in columns:
        obj[column.replace("\n","")] = fields[columns.index(column)].replace("\n","")
    objs[fields[0]] = obj

import json
with open('data.json','w') as outfile:
    json.dump(objs,outfile)
