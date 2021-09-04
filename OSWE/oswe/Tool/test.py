# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")
#A = [10, -10, -1, -1, 10]
A = [-1, -1, -1, 1, 1, 1, 1]
#A = [5, -2, -3, 1]
def solution(A):
	# write your code in Python 3.6
	times = 0
	for i in range(len(A)):
		times = check(A, times, balance=0)
	print (times)
def check(A,times,balance):
	for i in A:
		balance += i
		if balance >= 0:
			pass
		else:
			times = times +1
			A.append(min(A))
			A.remove(min(A))
			break
	return times

solution(A)