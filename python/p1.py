'''
'
' Description: An experiment to test the results (difference in weighted averages) from using Breadth First Search vs
'              Prim's Algorithm. Uses an adjacency list to track results.
'
'''

import random


'''
' Function: adjacency
' Description: Constructs a randomized weighted adjacency list based on the input integer,
' the number of vertices in the graph. Uses the algorithm as mentioned in the assignment.
' Each entry for a vertex typically looks like this: [False,[vertex,weight],[vertex,weight]...etc]
' The first entry for every vertex is a boolean as a placeholder for the BFS algorithm.
'
' Input: n, an integer describing the number of vertices in the graph.
' Output: lis, a randomly generated weighted adjacency list with n vertices, using
' randomly generated weights from 10 to 100. 

'''
def adjacency(n):
  lis = []

  # Start all vertices with a boolean so the BFS algorithm can track which vertices
  # it has already been to
  for k in range (0,n):
    lis.append([False])

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
      # Determine the weight of the edge
      weight = random.randint(10,100)
      # Add the randomized values to the adjacency list
      lis[i].append([rand,weight])
      lis[rand].append([i,weight])

  return lis


'''
' Function: test
' Description: Simply outputs the test adjacency matrix into an adjaceny matrix form
' that can be used with my functions.
' Input: N/A
' Output: testlis, the test adjacency matrix as given in the assignment.
'''
def test():
  testlis = [[False,[1,15],[3,7],[4,10]],[False,[0,15],[2,9],[3,11],[5,9]],[False,[1,9],[4,12],[5,7]],[False,[0,7],[1,11],[4,8],[5,14]],[False,[0,10],[2,12],[3,8],[5,8]],[False,[1,9],[2,7],[3,14],[4,8]]]
  return testlis


'''
' Function: bfs
' Description: Given an adjacency list in the specified format, finds its spanning tree
' using the BFS algorithm, and outputs its total edge weight.
'
' Input: graph, a generated adjacency list. Must be in the format as specified in
' the function adjacency.
' Output: total, the total edge weight of the input adjacency list using the BFS algorithm.
'''
def bfs(graph):
  Q = []
  
  # Start at some random vertex
  v = random.randint(0,len(graph)-1)
  
  # Make sure we haven't selected a vertex that doesn't point to anything.
  # If the length of the list is 1, that means only the true/false element is
  # in that list and the vertex is not pointing at anything.
  # Therefore, choose another vertex.
  while (len(graph[v]) == 1):
    v = random.randint(0,len(graph)-1)

  # We have visited the current vertex. Add it to the queue
  graph[v][0] = True
  Q.append(v)
  total = 0

  # All of this simply follows the BST algorithm as shown in class,
  # with the addition of the summation of edge weights and implementation
  # of the boolean.
  while Q != []:
    # Pop off the topmost vertex in the stack
    x = Q[0]
    del Q[0]
    # Cannot traverse a boolean. If the length of the current vertex
    # is 1, that means there's only the True/False statement and no neighbours,
    # so we can skip it/basically just stop there.
    if len(graph[x]) != 1:
      for i in range (1,len(graph[x])):
        y = graph[x][i][0]
        if graph[y][0] == False:
          graph[y][0] = True
          Q.append(y)
          weight = graph[x][i][1]
          # Sum up all edge weights
          total += weight

  
  return total


'''
' Function: prim
' Description: Given an adjacency list in the specified format, finds its spanning tree
' using Prim's Algorithm, and outputs its total edge weight. The 4th solution,
' the use of a data table as discussed in class, was used in this case.
'
' Input: graph, a generated adjacency list. Must be in the format as specified in
' the function adjacency.
' Output: total, the total edge weight of the input adjacency list.
'''
def prim(graph):
  tracker = [[],[]]

  # Set up the tracking table
  # I have completely removed the 2nd row, the connector,
  # from the code as it is not necessary in this case as
  # we are only looking at edge weights and no actual information
  # about the edges are needed.
  # 0 = If the vertex has not yet been visited.
  # 1 = Lowest edge weight to its connector. As shown in the function
  # adjacency, max weight is 100, so this placeholder edge weight of 101
  # will always be replaced as we are looking for min weights.
  for i in range (len(graph)):
    tracker[0].append('Y')
    tracker[1].append(101)

  # Start at some random vertex
  v = random.randint(0,len(graph)-1)
  
  # Make sure we haven't selected a vertex that doesn't point to anything.
  # If the length of the list is 1, that means only the true/false element is
  # in that list and the vertex is not pointing at anything.
  # Therefore, choose another vertex.
  while (len(graph[v]) == 1):
    v = random.randint(0,len(graph)-1)

  # We have visited the current vertex
  tracker[0][v] = 'N'

  # Add all neighbours of the current vertex to the table
  for j in range (1,len(graph[v])):
    vert = graph[v][j][0]
    tracker[1][vert] = graph[v][j][1]

  # Both the actual MST and edge lists are not needed in this case,
  # so I have removed them.
  total = 0

  # All of this simply follows the Prim's algorithm as shown in class.
  while len(mst) < len(graph):
    # Find the neighbour that has the smallest edge weight.
    minval =  min(tracker[1])
    minvertex = tracker[1].index(minval)
    # Sum up all minimum edge weights of the MST
    total += minval
    # We have visited the vertex
    tracker[0][minvertex] = 'N'
    tracker[1][minvertex] = 101

    # Add weights of neighbours that we have not yet visited to the table
    for k in range (1,len(graph[minvertex])):
      neighbour = graph[minvertex][k][0]
      weight = graph[minvertex][k][1]
      if tracker[0][neighbour] is 'Y' and weight < tracker[1][neighbour]:
        tracker[1][neighbour] = weight

  return total

'''
' Function: expt
' Input: N/A
' Output: Print statements from the experiment runs, as explained in the
' assignment notes. For my run, I set this to 1000 runs each because I knew
' my computer can handle it pretty well, but I lowered it to 100 in this submission
' so it might run quickly on slower computers.
'''
def expt():
  # Run experiment k times to minimize error with averages
  k = 100
  
  for i in range (5):
    # Number of vertices, as described in the assignment notes:
    # n = 20,30,40,50,60
    n = i*10+20
    difftotal = 0
    for j in range(k):
      graph = adjacency(n)
      bfsweight = bfs(graph)
      primweight = prim(graph)
      # Sum up difference percentages
      difftotal += (bfsweight/primweight-1)*100

    # Find the average difference percentage
    avgdiff = difftotal/k
    print 'Avg percentage (from',k,'runs) of ratio between total edge weights of BFS vs Prim using a graph containing',n,'vertices:',avgdiff,'%'
