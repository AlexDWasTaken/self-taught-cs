f(x, y) = 10*x + y



[ [1 2]; [3 4]; [5 6] ]
[ [1, 2] [3, 4] [5, 6] ]
[1, 2]
[1 2]
[1; 2]
[[1 2] [3 4] [5 6]]
[1 2 3 4 5 6]

[1, 2]
[1 2]
[1; 2]
[1 2 3 4 5 6]
[[1 2], [3 4], [5 6]]
[[1 2] [3 4] [5 6]]

[1 2]
[[1 2], [3 4], [5 6]]
[[1 2]; [3 4]; [5 6]]
[[1 2] [3 4] [5 6]]
[[1, 2] [3, 4] [5, 6]]
[[1, 2], [3, 4], [5, 6]]

[[1, 2]; [3, 4]; [5, 6]]

[
    1 2 3 4 5
    6 7 8 9 10
    11 12 13 14 15
]
[
    1   2   3   4   5
    6   7   8   9   10
    11  12  13  14  15
]

sum([1.1, 1.2])


            [
				extend(M, x + i, y + j) * offset_k[-i, -j]
				for i in -lᵢ:lᵢ,
				for j in -lⱼ:lⱼ
			]

            function convolve(M::AbstractMatrix, K::AbstractMatrix)
                num_rows, num_columns = size(K)
                lᵢ = (num_rows - 1)/2
                lⱼ = (num_columns - 1)/2
                M_rows, M_columns = size(M)
                offset_k = OffsetArray(K, -lᵢ:lᵢ, -lⱼ:lⱼ)
                convolved = 
                [
                    extend(M, x + i, y + j) * offset_k[-i, -j]
                    for i in -lᵢ:lᵢ,
                    for j in -lⱼ:lⱼ
                ]
                return convolved
            end