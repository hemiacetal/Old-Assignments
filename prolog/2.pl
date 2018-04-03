
% Predicate: q2, pathfind
% Description:	Program for Question 2. Finds an acyclic path of a given
%		graph as outlined in the predicate graph. The graph
%		predicate is currently outlined with the graph for
%		Question 3 of the assignment, with the costs removed.
%		q2 sets the default parameters to run pathfind, the
%		actual algorithm to find a path.
%
% Format (for q2): q2(Start node, End node, The path between the two
%                  nodes).
%
% Format (for pathfind): pathfind(Start node, End node, Nodes we have
%			 already checked, The path between the two
%			 nodes.
q2(A,Z,Path) :-
	pathfind(A,Z,[],Path).

pathfind(A,Z,Placeholder,Path) :-
	A \= Z,
	graph(A,B),
	not(member(B,Placeholder)),
	( B = Z, Path = [A,B];
	 B \= Z, pathfind(B,Z,[A|Placeholder],Next), Path = [A|Next]).

% The rules for the graph for q2. By default, this is the graph for
% Question 3. Defines the edges of the graph, i.e. graph(a,b) represents
% a directed edge from node a to b.
graph(a,b).
graph(b,c).
graph(b,d).
graph(c,d).
graph(d,b).


% Predicate: q3, costpathfind, compcost
% Description:	Finds the minimum cost path
%		between two given nodes of the graph as described in the
%		assignment. q3 sets the default parameters to run
%		costpathfind, the actual algorithm to find the
%		path. costpathfind is just a modified version of
%		pathfind such that it includes the cost of the path. The
%		paths are then sorted in ascending order of cost through
%		the use of the compcost predicate, and the first one of
%		the list is output as the path with minimum cost.
%
% Format (for q3): q3(Start node, End node, The path between the two
%                  nodes,The associated cost).
%
% Format (for costpathfind): costpathfind(Start node, End node, Nodes we
%			   have already checked, The path between the
%			   two nodes, The associated cost).
%
% Format (for compcost): compcost(The two lists sorted by
%			 the first element in ascending order, The
%		         first list being compared, The second
%			 list being compared to).

q3(A,Z,MinPath,MinCost) :-
	findall([Cost|Path], costpathfind(A,Z,[],Path,Cost), Paths),
	predsort(compcost,Paths,[[MinCost|MinPath]|_]).

costpathfind(A,Z,Placeholder,Path,Cost) :-
	edge(A,B,Val),
	not(member(B,Placeholder)),
	(A = Z, Path = [A], Cost is 0;
	 B = Z, Path = [A,B], Cost is Val;
	 B \= Z, costpathfind(B,Z,[A|Placeholder],Next,NextVal), Path = [A|Next], Cost is Val + NextVal ).

compcost(Sort,[X|_],[Y|_]) :- compare(Sort,X,Y).

% The rules for question 3. These are the edges of the graph as
% defined in the assignment.
% I placed the b to d edge with the larger cost first to test that
% finding the minimum cost works, rather than just using the first path
% it finds in what is defined.
edge(b,d,5).
edge(a,b,3).
edge(b,c,1).
edge(c,d,2).
edge(d,b,2).


% Predicate: q4, syllables
% Description:	Separates words into syllables
%		based on the vowel-consonant-vowel and
%		vowel-consonant-consonant-vowel cases as defined in
%		the assignment notes. q4 separates a word into a list of
%		characters, which then can be run through the syllables
%		predicate. syllables sorts the given list by the two
%		cases as defined, and concatenates recursive results.
%
% Format (for q4): q4(A word in all lowercases, The word separated into
%                  syllables).
%
% Format (for syllables): syllables(A list of characters, The separated
%                         syllables).

q4(X,Syllables) :-
	atom_chars(X, CharList),
	syllables(CharList,Syllables).

syllables([],'') :- !.
syllables([A,B,C|Xs],Result):-
	vowel(A), consonant(B), vowel(C),
	Xs \= [],
	syllables(Xs,Next),
	atomic_concat(A,B,First),
	atomic_concat(First,C,Second),
	atomic_concat(Second,-,Third),
	atomic_concat(Third,Next,Result).
syllables([A,B,C,D|Xs],Result):-
	vowel(A), consonant(B), consonant(C), vowel(D),
	syllables(Xs,Next),
	atomic_concat(A,B,First),
	atomic_concat(First,-,Second),
	atomic_concat(Second,C,Third),
	atomic_concat(Third,D,Fourth),
	atomic_concat(Fourth,Next,Result).
% While I could have implemented a cut here to reduce the amount of "X =
% the entire given word," it also removes some of the other correct
% separations, i.e. with the cut, q4(temporary,X) results in just X =
% tem-porary rather than X = tem-porary, tempora-ry, temporary, so I
% left the cut out.
syllables([X|Xs],Result):-
	syllables(Xs,Next),
	atomic_concat(X,Next,Result).

% The rules for question 4, sorting letters by vowel/consonant.
vowel(a).
vowel(e).
vowel(i).
vowel(o).
vowel(u).
vowel(y).

consonant(b).
consonant(c).
consonant(d).
consonant(f).
consonant(g).
consonant(h).
consonant(j).
consonant(k).
consonant(l).
consonant(m).
consonant(n).
consonant(p).
consonant(q).
consonant(r).
consonant(s).
consonant(t).
consonant(v).
consonant(w).
consonant(x).
consonant(z).






