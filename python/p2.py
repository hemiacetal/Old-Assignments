'''
'
' Description:  Experiment for testing efficent vertex cover calculation. Compares using a brute force algorithm (BFVC),
'               an optimized brute force algorithm (BFVCOptimized), and an approximation algorithm (approxVC).
'
'''

import sys
import time
import random
import itertools

'''
' Function: subsets
' Description: Finds all the subsets from 0 to the specified number-1.
'               A subfunction for the BFVC and BFVCOptimized.
'
' Input: n, some integer (in this case the number of vertices)
' Output: all subsets of the set [0 ... n-1]
'''
def subsets(n):
        
        set = []

        for i in range (0, n):
                set.append(i)

        return reduce(lambda a, b:a+[c+[b] for c in a], set, [[]])


'''
' Function: edges
' Description: Provides all the edges that is in a given graph.
'
'
' Input: graph, an adjacency list for an undirected graph
' Output: edges, a list of the edges in the given graph.
'''
def edges(graph):
        
        edges = []
        
        for i in range (len(graph)):  
                if len(graph[i]) != 0:
                        for j in (graph[i]):
                                # No duplicate edges allowed
                                if [i,j] not in edges and [j,i] not in edges and i != j:
                                        edges.append([i,j])

        return edges

                                                
'''
' Function: BFVC
' Description: Brute force vertex cover, following my algorithm as detailed in the DOCX report.
'               Finds the optimal vertex cover by looping through every possible subset.
'
'
' Input: graph, an adjacency list for an undirected graph,
'        k, some specified size for the vertex cover.
' Output: If there is a cover < k, subset[i], the vertex cover set, or False, when there is no
'               cover <= k.
'''
def BFVC(graph,k):

        # Find all subsets
        subset = subsets(len(graph))
        min = sys.maxint
        index = 0 

        # Loop for the total number of subsets
        for i in range (0,len(subset)):
                # Make a copy of the edges to delete
                check = edges(graph)

                # Loop for each edge in the vertex
                for j in (subset[i]):
                        for m in (graph[j]):
                                # Remove the indicated edges
                                if [j,m] in check:
                                        check.remove([j,m])
                                if [m,j] in check:
                                        check.remove([m,j])
                                        
                # Find the smallest vertex cover
                if check == []:
                        if min > len(subset[i]):
                                min = len(subset[i])
                                index = i

        if min > k:
                # We don't have a cover of k length.
                return False
        else:
                # We have a cover of at least k length.
                return subset[index]
        
'''
' Function: textbook
' Description: Tests BFVC with the example as found on page 1109 of the text.
'
' Input: none
' Output: Results of BFVC with this graph and k = 3
'''
def textbook():
        a = [[1],[0,2],[1,3,4],[2,4,5,6],[2,3,5],[3,4],[3]]
        return BFVC(a,3)

'''
' Function: subsetsOptimized
' Description: Finds all the subsets up to length k from 0 to the specified number-1.
'
' Input: n, some integer (in this case the number of vertices)
'        k, the max length of a subset.
' Output: all subsets of the set [0 ... n-1]
'''
def subsetsOptimized(n, k):
        
        set = []

        for i in range (1, k+1):
                set += list(itertools.combinations(range(n),i))

        
        return set

'''
' Function: BFVCOptimized
' Description: Optimized version of the BFVC function.
'               Basically the same function, except it sorts the subsets by increasing length,
'               and either stops when it finds the first working cover, or stops when the length
'               of the subset > k.
'
'
' Input: graph, an adjacency list for an undirected graph,
'        k, some specified size for the vertex cover.
' Output: If there is a cover < k, subset[i], the vertex cover set, or False, when there is no
'               cover <= k.
'''
def BFVCOptimized(graph,k):

        # Find and sort all subsets by increasing length
        subset = subsetsOptimized(len(graph),k)
        subset.sort(key = len)

        # Loop for the total number of subsets
        for i in range (0,len(subset)):

                # We don't have a vertex cover of at least k length. Stop running. 
                if len(subset[i]) > k:
                        return False
                
                # Make a copy of the edges to delete
                check = edges(graph)

                # Loop for each edge in the vertex
                for j in (subset[i]):
                        for m in (graph[j]):
                                if [j,m] in check:
                                        check.remove([j,m])
                                elif [m,j] in check:
                                        check.remove([m,j])

                # We've found the vertex cover. Return this subset.       
                if check == []:
                        return subset[i]

        # Somehow we've traversed to the end of the subset length anyway.
        # Happens when k > len(subset).
        return False

'''
' Function: textbookOptimized
' Description: Tests BFVCOptimized with the example as found on page 1109 of the text.
'
' Input: none
' Output: Results of BFVCOptimized with this graph and k = 3
'''
def textbookOptimized():
        a = [[1],[0,2],[1,3,4],[2,4,5,6],[2,3,5],[3,4],[3]]
        return BFVCOptimized(a,3)

'''
' Function: approxVC
' Description: Using the approximation algorithm as seen in CLRS page 1109,
'               outputs an approximate vertex cover.
'
' Input: graph, an adjacency list for an undirected graph.
' Output: C, the approximated vertex cover set.
'''
def approxVC(graph):
        
        C = []
        # Get all the edges of the graph
        E = edges(graph)

        # Loop until we have no more edges
        while E != []:
                # Select an arbitrary edge {u, v} in E
                x = random.randint(0,len(E)-1)

                # Put u and v into C if they're not already there
                u = E[x][0]
                v = E[x][1]
                if u not in C:
                        C.append(u)
                if v not in C:
                        C.append(v)

                # Remove edges that contain u and v
                E = filter(lambda x:x[0] != u and x[1] != u and x[0] != v and x[1] != v,E)

        return C
                
'''
' Function: adjacency
' Description: Constructs a randomized adjacency list based on the input integer,
' the number of vertices in the graph.
'
' Input: n, an integer describing the number of vertices in the graph.
' Output: lis, a randomly generated adjacency list with n vertices.
'''
def adjacency(n):
        
        lis = []
        
        for k in range (0,n):
                lis.append([])

        rand = 0

        # Construct the randomized adjacency list with n vertices
        for i in range (1,n):
                # Determine the number of vertices the current vertex is connected to
                x = random.randint(0,i-1)
                # Placeholder to tell what vertices we have connected to the current vertex
                curr = []

                for j in range (x):
                        # Determine the connected vertex
                        rand = random.randint(0,i-1)
                        # Ensures we do not have a vertex that points to the same vertex
                        # more than once.
                        # Rerolls if the current random number is already listed as a
                        # edge to the vertex.
                        if curr != []:
                                while ([x for x in curr if x == rand] != []):
                                        rand = random.randint(0,i-1)

                curr.append(rand)
                # Add the randomized values to the adjacency list
                lis[i].append(rand)
                lis[rand].append(i)

        return lis

'''
' Function: runtimeA
' Description: Prints a table detailing runtime in seconds for n vertices
'               for the BFVC function.
'
' Input: none
' Output: print statements detailing runtime in seconds
'''
def runtimeA():
        
        n = 5,10,15,20
        
        print('n\t|\tBFVC')
        for i in n:
                # Make an adjacency list with i vertices
                graph = adjacency(i)
                # Choose some limit k
                rand = random.randint(0,i-1)
                
                start = time.time()
                BFVC(graph,rand)
                a = time.time() - start
                
                print(str(i)+'\t|'+str(a))


'''
' Function: runtimeB
' Description: Prints a table detailing runtime in seconds for n vertices
'               for the BFVCOptimized function.
'
' Input: none
' Output: print statements detailing runtime in seconds
'''
def runtimeB():
        
        n = 5,10,15,20,25,30

        print('n\t|\tBFVCOptimized')
        for i in n:
                # Make an adjacency list with i vertices
                graph = adjacency(i)
                # Choose some limit k
                rand = random.randint(0,i-1)
                
                start = time.time()
                BFVCOptimized(graph,rand)
                a = time.time() - start
                
                print(str(i)+'\t|'+str(a))

'''
' Function: runtimeC
' Description: Prints a table detailing runtime in seconds for n vertices
'               for the approxVC function.
'
' Input: none
' Output: print statements detailing runtime in seconds
'''
def runtimeC():
        
        n = 100,500,1000,1500,2000,2500,3000
        
        print('n\t|\tapproxVC')
        for i in n:
                # Make an adjacency list with i vertices
                graph = adjacency(i)
                # I didn't set limits for the approximation algorithm
                
                start = time.time()
                approxVC(graph)
                a = time.time() - start
                
                print(str(i)+'\t|'+str(a))

                
                
