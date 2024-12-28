Part 1
a=[input]
(0..a.max).map{|x| a.map{|y| (x-y).abs}.sum}.min

Part 2
a=[input]
(0..a.max).map{|x| a.map{|y| ((x-y).abs*((x-y).abs+1))/2}.sum}.min