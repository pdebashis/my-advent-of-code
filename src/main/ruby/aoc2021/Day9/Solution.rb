#Part 1
i=[]
File.open("testcase","r").each do |line|
        i << line.strip.chars.map(&:to_i)
end

i.each do |x|
        x << 9
        x.unshift 9
end

adding=[]
(0..i.first.size-1).each do |x|
  adding<<9
end
i<<adding
i.unshift adding

answers=[]
i.each_with_index do |val,idx|
        val.each_with_index do |val2,idx2|
                if idx!=0 and idx != i.size-1 and idx2!=0 and idx2 != val.size-1
                     answers<<val2 if val2 < val[idx2-1] && val2 < val[idx2+1] && val2 < i[idx+1][idx2] && val2 < i[idx-1][idx2]
                     print "#{val2}"
                end
        end
end

p answers.sum +answers.size
 
#Part 2
$glob=i

def return_answers(x,y)
    p "looping #{x} #{y}"
    if $glob[x][y] == 9 or $glob[x][y] == -1 or x==0 or y==0 or y==$glob.first.size-1 or x==$glob.size-1
        []
    else
        a = $glob[x][y]
        $glob[x][y]=-1
        [[a],return_answers(x-1,y),return_answers(x+1,y),return_answers(x,y+1),return_answers(x,y-1)].flatten
    end
end

answers=[]
i.each_with_index do |x,i|
        x.each_with_index do |y,i2|
                answers << return_answers(i,i2)
        end
end

p answers.filter{|x| x.size>0}.sort_by{|x| x.size}[-3..-1].map{|x| x.size}