#Part 1
points = {")"=>3,"]"=>57,"}"=>1197,">"=>25137}

input = []
stack = []

def compliment x
  return "(" if x.eql? ")"
  return "{" if x.eql? "}"
  return "[" if x.eql? "]"
  return "<" if x.eql? ">"
end

answer = []

File.open("testcase1","r").each do |line|
error = ""
line.chomp.chars.each do |x|
  case x
  when /[\(\{\[<]/
    stack.push x
  when /[\)\}\]>]/
    if stack.last == compliment(x)
      stack.pop
    else
      error=x
    end
  end
  break unless error.empty?
end
answer << error unless error.empty?
end

p answer.map{ |x| points[x]}.sum

#Part 2
points_2 = {")"=>1,"]"=>2,"}"=>3,">"=>4}

input = []
stack = []
answer = []

def compliment x
  return "(" if x.eql? ")"
  return "{" if x.eql? "}"
  return "[" if x.eql? "]"
  return "<" if x.eql? ">"
end


def compliment_2 x
    return ")" if x.eql? "("
    return "}" if x.eql? "{"
    return "]" if x.eql? "["
    return ">" if x.eql? "<"
  end

File.open("testcase","r").each do |line|
p line.chomp
error = ""
line.chomp.chars.each do |x|
  case x
  when /[\(\{\[<]/
    stack.push x
  when /[\)\}\]>]/
    if stack.last == compliment(x)
      stack.pop
    else
      error=x
    end
  end
  break unless error.empty?
end
input << stack.join("") if error.empty?

stack.clear
end

input.each do |x|
    answer << x.chars.reverse.map{|x| compliment_2(x)}.join
end
p answer
answer2=[]

answer.each do |x|
    score=0
    x.chars.each do |y|
        score=score*5+points_2[y]
    end
    answer2 << score
end

answer2.sort!
len = answer2.size

p answer2[len/2]