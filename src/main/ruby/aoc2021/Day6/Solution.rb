i=[]
File.open("testcase","r").each do |line|
        i=line.split(",").map(&:to_i)
end
days=256
result=0
memory={}
def find_count fish,days,memory
        return 1 if days <= fish
        return memory["#{fish},#{days}"] unless memory["#{fish},#{days}"].nil?
        memory["#{fish},#{days}"] = find_count(6,days-fish-1,memory) + find_count(8,days-fish-1,memory)
end

i.each do |x|
   result += find_count(x,days,memory)
end

p result