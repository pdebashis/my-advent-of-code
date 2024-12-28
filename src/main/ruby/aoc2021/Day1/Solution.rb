## Part 1
while read line;do if [ $line -gt $prev_line ]; then echo "${line} increased";else echo "${line} decreased"; fi; prev_line=$line; done < input_file | grep increased | wc -l

## Part 2
a=[]

File.open("testcase","r").each do |line|
  row=line.to_i
  a << row
end

ctr=0

a.each_with_index do |x,y|
  break if y+4 > a.size
  curr = x + a[y+1] + a[y+2]
  nex = a[y+1] + a[y+2] + a[y+3]
  ctr += 1 if curr < nex
  p "ctr = #{ctr}"
end