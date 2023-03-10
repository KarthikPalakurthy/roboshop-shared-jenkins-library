def abc = "10"
println abc

println "If condition loop"

if (abc=="10"){
 println "Yes"
}else{
 println "No"
}

println "While loop"

def a = 7
def b = 2
while(a > b){
 println "${b}"
 b++
}

println "For loop"

for (int i =0; i<=5; i++){
 println "${i}"
}

println "Defining an array"

def fruits = ["apple","mango", "orange", "pineapple"]
for (i in fruits){
 println(i);
}