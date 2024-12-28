part 1

a=[]
File.open("testcase","r").each do |line|
  a << line.strip
end
def get_gamma arr
  a=[]
  last = 0
  arr.each do |elem|
    elem.chars.each_with_index do |x,i|
    a[i] = "#{a[i]}#{x}"
    last = i
  end
  end
  n = []
  gamma=""
  (0..last).each do |i|
    n[i] = a[i].scan("0").length > a[i].scan("1").length ? 0 : 1
    gamma = "#{gamma}#{n[i]}"
  end
 
  return gamma
end

gamma = get_gamma a
epsilon=gamma.gsub(/\d/) {|bit| bit=="1"?"0":"1"}
gamma.to_i(2) * epsilon.to_i(2)


Part 2
def get_gamma arr,pos
  a=""
  last = 0
  arr.each do |elem|
    digit = elem.chars[pos]
    a = "#{a}#{digit}"
  end

  n = a.scan("0").length > a.scan("1").length ? 0 : 1
  n = 1 if a.scan("0").length == a.scan("1").length
 
  return n
end


def get_oxygen arr,start,last

 return arr[0].chars[start..last].join if arr.size==1
 return "" if start > last
 
   
 n = get_gamma arr,start
 new_arr = arr.filter{|x| x.chars[start] == "#{n}"}
 
 return "#{n}#{get_oxygen new_arr,start+1,last}"
 
 end

def get_epsilon arr,pos
  a=""
  last = 0
  arr.each do |elem|
    digit = elem.chars[pos]
    a = "#{a}#{digit}"
  end

  n = a.scan("0").length > a.scan("1").length ? 1 : 0
  n = 0 if a.scan("0").length == a.scan("1").length
 
  return n
end


def get_co2 arr,start,last

 return arr[0].chars[start..last].join if arr.size==1

 return "" if start > last
   
 n = get_epsilon arr,start
 new_arr = arr.filter{|x| x.chars[start] == "#{n}"}
 
 return "#{n}#{get_co2 new_arr,start+1,last}"
 
 end
oxy=get_oxygen a,0,11
co2=get_co2 a,0,11

oxy.to_i(2) * co2.to_i(2)