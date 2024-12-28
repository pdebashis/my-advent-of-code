#Part 1
input = []
File.open("testcase","r").each do |line|
  input << line.chomp.split("->").join(",").split(",").map(&:to_i)
end

mat = Hash.new
input.each do |arr|
  x1,y1,x2,y2 = arr

  if x1==x2
    big = y1 > y2 ? y1 : y2
    small = y1 > y2 ? y2 : y1
    (small..big).each do |y|
      mat["#{x1},#{y}"] += 1 unless mat["#{x1},#{y}"].nil?
      mat["#{x1},#{y}"] = 1 if mat["#{x1},#{y}"].nil?
    end
  end

  if y1==y2
    big = x1 > x2 ? x1 : x2
    small = x1 > x2 ? x2 : x1
    (small..big).each do |x|
       mat["#{x},#{y1}"] += 1 unless mat["#{x},#{y1}"].nil?
       mat["#{x},#{y1}"] = 1 if mat["#{x},#{y1}"].nil?
    end
  end

end

result=0
mat.values.each do |v|
  result+=1 if v > 1
end
result


#Part 2
input.each do |arr|
  x1,y1,x2,y2 = arr

  if x1==x2
    y1,y2 = y2,y1 if y2 < y1
    (y1..y2).each do |y|
      mat["#{x1},#{y}"] += 1 unless mat["#{x1},#{y}"].nil?
      mat["#{x1},#{y}"] = 1 if mat["#{x1},#{y}"].nil?
    end
  elsif y1==y2
    x1,x2 = x2,x1 if x2 < x1
    (x1..x2).each do |x|
       mat["#{x},#{y1}"] += 1 unless mat["#{x},#{y1}"].nil?
       mat["#{x},#{y1}"] = 1 if mat["#{x},#{y1}"].nil?
    end
  elsif (x2-x1).abs == (y2-y1).abs
    if x1 > x2
      x2,x1 = x1,x2
      y2,y1 = y1,y2
    end
    x = x1
    if y1 < y2
      (y1..y2).each do |y|
        mat["#{x},#{y}"] += 1 unless mat["#{x},#{y}"].nil?
        mat["#{x},#{y}"] = 1 if mat["#{x},#{y}"].nil?
        x+=1
      end
    else
      y1.downto(y2).each do |y|
        mat["#{x},#{y}"] += 1 unless mat["#{x},#{y}"].nil?
        mat["#{x},#{y}"] = 1 if mat["#{x},#{y}"].nil?
        x+=1
      end
     end
  end

end