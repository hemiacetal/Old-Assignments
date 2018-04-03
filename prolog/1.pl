/*
 *
 * Filename: Assignment1.pl
 */

% Predicate: fact
% Description:	Computes the factorial of a given number. 
% Format: fact(An integer, The factorial of the given integer)
%
fact(Num,Result) :- (Num = 0; Num = 1), Result is 1.
fact(Num,Result) :-
    Num \= 0, Num \= 1,
    Subt is Num-1,
    fact(Subt,X),
    Result is Num*X.


% Predicate: listind
% Description:	Recursively finds all indices in a given list
%		containing a given integer element, and then outputs these
%		indices as a list. Starts from an index of 1.
%		
% Format: listind(Integer element to search for, List to search, 
%		List of indices containing aforementioned element)
%
%	  Uses overloading past the first index to calculate the
%	  other indices, by subtracting the length of the list, containing 
%	  the index being checked to the end of the list, from the length of the
%	  original list.
%	  The format for this is:
%	  listind(Integer element to search for, Original list to search, 
%		Recursively cut list, List of indices containing aforementioned element)
%	  
%
listind(_,[],Result) :- Result = [].
listind(Num,[Num|Xs],Result) :-
    listind(Num,[Num|Xs],Xs,Next),
    Result = [1|Next].
listind(Num,[X|Xs],Result) :-
    X \= Num,
    listind(Num,[X|Xs],Xs,Result).
listind(_,_,[],Result) :- Result = [].
listind(Num,Original,[Num|Xs],Result) :-
    listind(Num,Original,Xs,Next),
    len(Original,A),
    len([Num|Xs],B),
    Y is A-B+1,
    Result = [Y|Next].
listind(Num,Original,[X|Xs],Result) :-
    X \= Num,
    listind(Num,Original,Xs,Result).

% Predicate: len
% Description:	Helper predicate for listind. As the assignment outline
%		states that no built-in predicate can be used, this
%		just finds the length of a given list. 
% Format: len(A list, Length of the list)
%
len([],Length) :- Length is 0.
len([_|Xs],Length) :-
    len(Xs,Next),
    Length is Next+1.

