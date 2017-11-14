def answer(l, t):
    sum = [0];
    # your code here
    for i in range(len(l)):
        sum.append(l[i] + sum[i]);
    
    for i in range(len(l)):
        for j in range(i, len(l)):
            if (sum[j + 1] - sum[i] == t):
                return i, j
    return -1, -1
