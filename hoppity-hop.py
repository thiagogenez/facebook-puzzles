#! /usr/bin/python
# coding: utf-8

if __name__ == "__main__":

	import sys

	for i in range(1, int(str(open(sys.argv[1]).next()))):
		if i % 5 == 0:
			if i % 3 == 0:
				print("Hop")
			else:
				print ("Hophop")
			
		elif i % 3 == 0:
			print("Hoppity")
