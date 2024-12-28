#Part 1
a=b=c=0
File.open("testcase","r").each do |x|
  amt=x.split[1].to_i
  case x.split[0]
  when "forward" then a+=amt;b+=amtc;p "add #{amtc} to Depth, total of #{b}"
  when "down" then c+=amt
  when "up" then c-=amt
   else 0
  end
end


#Part 2
File.open("input_file","r").each do |x|
  amt=x.split[1].to_i
  case x.split[0]
  when "forward" then a+=amt;b+=amtc;p "add #{amtc} to Depth, total of #{b}"
  when "down" then c+=amt
  when "up" then c-=amt
  else 0
  end
end

a*b