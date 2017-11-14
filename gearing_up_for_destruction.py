def verify(pegs, a, b):
    x = float(a) / b
    for i in range(1, len(pegs)):
        x = (pegs[i] - pegs[i - 1]) - x
        if (x < 1):
            return False
    return True

def answer(pegs):
    n = len(pegs)
    sum = 0
    for i in range(n - 1):
        sum += (-1)**i * (pegs[i + 1] - pegs[i])
    a = sum * 2
    b = 1
    if (n % 2 == 0):
        if (sum % 3 == 0): 
            a = a / 3
        else: 
            b = 3
    return [a, b] if verify(pegs, a, b) else [-1, -1]
