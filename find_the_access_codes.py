answer(l):
    # your code here
    n = len(l)
    left = [0] * n
    right = [0] * n
    for i in range(n - 1):
        for j in range(i + 1, n):
            if (l[j] % l[i] == 0):
                left[j] += 1
                right[i] += 1
    return sum(left[i] * right[i] for i in range(1, n - 1))
