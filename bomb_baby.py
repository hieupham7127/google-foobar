def findVal(a, b):
    if (b > 1 and a % b == 0):
        raise Exception("Sorry. It's impossible to find a solution")
    return a if b == 0 else (a/b + findVal(b, a % b))

def answer(M, F):
    # your code here
    try:
        return findVal(long(M), long(F)) - 2
    except: 
        return "impossible"
