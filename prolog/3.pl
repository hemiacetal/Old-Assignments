/*
 *
 * Solving the farmer problem with Prolog. 
 *
 */

% All facts needed for every valid state possible. States are in form
% "Farmer, Goat, Wolf, Cabbage" with values e or w, as stated within the
% assignment specifications.
init((e,e,e,e)).
goal((w,w,w,w)).
% Can't take the wolf or the cabbage at the start.
% Take the goat:
s((e,e,e,e),(w,w,e,e)).
s((w,w,e,e),(e,w,e,e)).

% From here, we have two choices:
% 1. Take the wolf w,
s((e,w,e,e),(w,w,w,e)).
% 2. or take the cabbage w,
s((e,w,e,e),(w,w,e,w)).

% Either way, we have to take the goat back:
% 1. Take goat back e leaving wolf w, bring cabbage w
s((w,w,w,e),(e,e,w,e)).
s((e,e,w,e),(w,e,w,w)).
% 2. Take goat back e leaving cabbage w, bring wolf w
s((w,w,e,w),(e,e,e,w)).
s((e,e,e,w),(w,e,w,w)).

% Finally, bring the goat w.
s((w,e,w,w),(e,e,w,w)).
s((e,e,w,w),(w,w,w,w)).

% Predicate: next
% Description: Finds the next valid state that has not been visited of a
%              given state.
% Format: next(+CurrentState,+CurrentVisitedStates,-NextState)
next(State,Visited,Next) :-
    s(State,Next),
    not(member(Next,Visited)).

% Predicate: dfs
% Description: Uses depth first search to find all paths of
%              the farmer problem. Initialization does not require any
%              arguments as paths are output with maplist and writeln.
% Format: dfs(),
%         or dfs(+Queue of States,-Valid Path) past initilization
dfs() :-
    init(Start),
    findall(Path,dfs([[Start]],Path),Paths),
    maplist(writeln,Paths).

dfs([[Start|Rest]|_],Path) :-
    goal(Start),
    reverse([Start|Rest],Path).
dfs([[Start|Rest]|X],Path) :-
    findall(Next,next(Start,[Start|Rest],Next),NextXs),
    maplist(unify([Start|Rest]),NextXs,Queue),
    append(Queue,X,MergedQueue),
    dfs(MergedQueue,Path).

% Predicate: bfs
% Description: Uses breadth first search to find all paths of
%              the farmer problem. Initialization does not require any
%              arguments as paths are output with maplist and writeln.
%              Basically the dfs predicate but with how items are added
%              to the queue changed.
% Format: bfs(),
%         or bfs(+Queue of States,-Valid Path) past initialization.

bfs() :-
    init(Start),
    findall(Path,bfs([[Start]],Path),Paths),
    maplist(writeln,Paths).

bfs([[Start|Rest]|_],Path) :-
    goal(Start),
    reverse([Start|Rest],Path).
bfs([[Start|Rest]|X],Path) :-
    findall(Next,next(Start,[Start|Rest],Next),NextXs),
    maplist(unify([Start|Rest]),NextXs,Queue),
    append(X,Queue,MergedQueue),
    bfs(MergedQueue,Path).

% Predicate: unify
% Description: Just for the dfs/bfs predicates for a quick
%              zip/append with maplist.
% Format: unify(+Anything,+Anything,-Concatenated List)
unify(A,B,[B|A]).
