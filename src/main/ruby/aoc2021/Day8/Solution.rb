Part 1
input = []
output = []
cnt=0
File.open("testcase","r").each do |line|
  input << line.chomp.split("|")[0]
  output << line.chomp.split("|")[1]
end
output.map(&:split).flatten.each do |x|
   cnt+=1 if x.chars.count == 2 or x.chars.count == 4 or x.chars.count == 3 or x.chars.count == 7
end
p cnt

Part 2
answer = {'a'=>'', 'b' => '', 'c' => '', 'd' => '', 'e' => '', 'f' => '', 'g' => ''}
segment_map = {'abcefg' => 0,
                'acdeg' => 2,
                'acdfg' => 3,
                'abdfg' => 5,
                'abdefg' => 6,
                'abcdfg' => 9
            }

def get_b_e_f(s)
  Hash[s.delete(' ').split('').group_by{ |c| c }.map{ |k, v| [k, v.size] }]
end

def get_a(a)
  c = a.split.filter{|x| x.chars.size < 4}.join
  Hash[c.chars.group_by{|x| x}.map{ |k,v| [k,v.size]}].key(1)
end

def get_c(a,f)
    one = a.split.filter{|x| x.chars.size == 2}.join
    one.chars.filter{|x| !x.eql?(f) }[0]
end

def get_d(a,b,c,f)
    four = a.split.filter{|x| x.chars.size == 4}.join
    four.chars.filter{|x| !x.eql?(b) and !x.eql?(c) and !x.eql?(f)}[0]
end

input.each_with_index do |digits,index|
  answer['a'] = get_a(digits)
  answer['b'] = get_b_e_f(digits).key(6)
  answer['e'] = get_b_e_f(digits).key(4)
  answer['f'] = get_b_e_f(digits).key(9)
  answer['c'] = get_c(digits,answer['f'])
  answer['d'] = get_d(digits,answer['b'],answer['c'],answer['f'])
  answer['g'] = get_b_e_f(digits).select{|k,v| v==7}.keys.filter{|x| !x.eql? (answer['d'])}[0]

  output[index] = output[index].split.map{|x| 
    if x.chars.count == 2
    "1"
    elsif x.chars.count == 4
    "4"
    elsif x.chars.count == 3 
    "7"
    elsif x.chars.count == 7
    "8"
    else
        segment_map[x.chars.map{|y| answer.invert[y]}.sort.join]
    end 
}
end

p output.map(&:join).map(&:to_i).sum