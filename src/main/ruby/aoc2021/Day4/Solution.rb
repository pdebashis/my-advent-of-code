nums=[...]

boards = {}
i=0
File.open("testcase","r").each do |line|
   i=i+1 and next if line.chomp.empty?
   boards[i] = [] if boards[i].nil?
   boards[i] << line.chomp.split.map(&:to_i)
end

def check_bingo board
    board.each do |row|
      return true if row.sum == -5
    end
    board.transpose.each do |col|
      return true if col.sum == -5
    end
    return false
end

result=nil
final=nil

nums.each do |n|
  boards.each_pair do |key,b|
    b.each do |row|
      row.map!{|x| x==n ? -1 : x}
    end
    result = b and break if check_bingo(b)
  end

  final=n
  break unless result.nil?
end

result.flatten.map{|x| x==-1? 0 : x}.sum * final

#Part2

result=nil
second=nil
del_key=[]

nums.each_with_index do |n,i|
  boards.each_pair do |key,b|
    b.each do |row|
        row.map!{|x| x==n ? -1 : x}
      end
      del_key << key if check_bingo(b)
  end

  del_key.each do |k|
    result = boards.delete(k)
    second=n
  end

  p "boards_left=>#{boards.keys.size}"
  break if boards.keys.size == 0
end

result.flatten.map{|x| x==-1? 0 : x}.sum * second