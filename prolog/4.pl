
% Predicate:	sublistSum
% Description:	Finds all sublists of the given list that have a
%				sum equal to the number given.
%				Uses the helper predicate sublis to find all
%				sublists of the given list.
% Format:		sublistSum(List of Numbers,A number used as the
%			sum,Output sublists)
%
sublistSum(A,Num,B) :-
	sublis(A,B),
	sum_list(B,Num).

% Predicate:	sublis
% Description:	Helper predicate for sublistSum.
%				Finds all sublists.
% Format:		sublistSum(Some List,Sublists of said list)
%
sublis([],[]).
sublis([X|Xs],[X|Ys]):-
  sublis(Xs,Ys).
sublis([_|Xs],Ys):-
  sublis(Xs,Ys).

% Predicate:	matching
% Description:	Find matching indices of two given lists.
%				Uses the helper function matchfoo to actually
%				find these index values, this just puts it into
%				a list.
% Format:		matching(List1,List2,A list of indices where the
%			two list match)
%
matching(Lis1,Lis2,Indices) :-
	findall(Ind,matchfoo(Lis1,Lis2,Ind),Indices).

% Predicate:	matchfoo
% Description:	Helper predicate for matching.
%				Finds the index where two lists match
%				through the use of nth0.
% Format: matchfoo(List1,List2,An index where the two lists match)
%
matchfoo(X,Y,Ind) :-
	nth0(Ind,X,A),
	nth0(Ind,Y,B),
	A =:= B. % Choose this index iff the A = B numerically

% Predicate:	legalCourse
% Description:	Checks if a course list is legal or not
%				based on specified parameters in the
%				assignment page.
%				Uses legalChk as a helper predicate to check
%				if there exist more than one projects with
%				the same pair of students.
% Format:		legalCourse(Course List), outputs true or false.
%
legalCourse([]).
legalCourse([Stu1+Stu2+_|Xs]) :-
	Stu1 \= Stu2,
	legalChk(Stu1,Stu2,Xs),
	legalCourse(Xs).

% Predicate:	legalChk
% Description:	Checks if there are duplicate pairs in the
%				tail of the course list, as per how
%				legalCourse is run, by comparing the
%				original tail list with the tail list
%				that have removed all counts of supposed
%				duplicates.
% Format: legalChk(First Student,Second Student,Course List)
%
legalChk(Stu1,Stu2,Course) :-
		subtract(Course,[Stu1+Stu2+_],Subt),
		subtract(Subt,[Stu2+Stu1+_],Subtfinal),
		Course = Subtfinal.

% Predicate:	studentCount
% Description:	Counts the total number of projects the given
%				student has worked on from the course list.
%				Uses name1 and name2 as helper predicates to
%				separate the course list into lists of
%				names and then uses count to count the
%				total number of times the name shows up
%				in both lists.
% Format: studentCount(Some Name,Course List,Int of times the name
% appears)
%
studentCount(Name,Course,Count) :-
	name1(Course, Name1lis),
	name2(Course, Name2lis),
	count(Name,Name1lis,X),
	count(Name,Name2lis,Y),
	Count is X+Y.

% Predicate:	partners
% Description:	Finds a list of students the given student has
%				worked with.
% Format:		partners(Some Name,Course List,List of Name's partners)
%
partners(_,[],A) :- A = [].
partners(Name,[Stu1+Stu2+_|Xs],Partners) :-
	Name = Stu1, % Stu2 is therefore a partner
	append([Stu2],B,Partners),
	partners(Name,Xs,B).
partners(Name,[Stu1+Stu2+_|Xs],Partners) :-
	Name = Stu2, % Stu1 is therefore a partner
	append([Stu1],B,Partners),
	partners(Name,Xs,B).
partners(Name,[Stu1+Stu2+_|Xs],Partners) :-
	Name \= Stu1, % This name is not in this project, skip
	Name \= Stu2,
	partners(Name,Xs,Partners).

% Predicate:	studentAvg
% Description:	Finds the average of marks that the input
%				student has from the input course list.
%				Uses marks as a helper predicate to find all
%				marks in the course.
% Format:		courseAvg(Course List,Average as a float)
courseAvg([], Avg) :- Avg = 0.
courseAvg(Course,Avg) :-
	marks(Course,Marks),
	sum_list(Marks,Sum),
	length(Course,Num),
	Avg is Sum/Num.

% Predicate:	studentAvg
% Description:	Finds the average of marks that the input
%				student has from the input course list.
%				Uses stuMarks as a helper predicate to
%				find all marks of the given student and
%				studentCount to find the number of marks
%				the student has received. Format:
%				studentAvg(some name,Course List,Average
%				as a float)
%
studentAvg(_,[], Avg) :- Avg = 0.
studentAvg(Name,Course,Avg) :-
	stuMarks(Name,Course,Marks),
	sum_list(Marks,Sum),
	studentCount(Name,Course,Count),
	Avg is Sum/Count.

% Predicate:	students
% Description:	Finds the list of students given a course list.
%				Uses name1 and name2 as a helper
%				predicate to merge all names of the
%				given course list and then remDupe to
%				remove duplicate names in the list.
%				Format: student(Course List,List of
%				students)
%
students(Course,Lis) :-
	name1(Course,A),
	name2(Course,B),
	append(A,B,Dupe),
	remDupe(Dupe,Lis).

% Predicate:	stuMarks
% Description:	Helper predicate for studentAvg.
%		Used to output a list of a given student's
%		marks.
% Format:	stuMarks(some name,Course List,List of Marks)
%
stuMarks(_,[],Marks) :- Marks = [].
stuMarks(Name,[Stu1+_+Mark|Xs],Marks) :-
	append([Mark],B,Marks), % Name is the first student.
	stuMarks(Name,Xs,B),
	Name = Stu1.
stuMarks(Name,[_+Stu2+Mark|Xs],Marks) :-
	append([Mark],B,Marks), % Name is the second student.
	stuMarks(Name,Xs,B),
	Name = Stu2.
stuMarks(Name,[Stu1+Stu2+_|Xs],Marks) :-
	Name \= Stu1, % Name is neither, skip.
	Name \= Stu2,
	stuMarks(Name,Xs,Marks).

% Predicate:	remDupe
% Description:	Helper predicate for students.
%		Used to remove any duplicates in a list.
% Format:	remDupe(Some list with duplicates,List without duplicates)
%
remDupe([], []).
remDupe([X|Xs], [X|Ys]) :-
	subtract(Xs,[X],As), % Subtract all duplicates from the tail of the list.
	remDupe(As,Ys).

% Predicate:	name1
% Description:	Helper predicate.
%		Puts the first names in the course list
%		into an actual list.
% Format: name1(Course list,list of names)
%
name1([],A) :- A = [].
name1([X1+_+_|Xs], A) :-
	append([X1],B,A),
	name1(Xs,B).

% Predicate:	name2
% Description:	Helper predicate.
%		Puts the second names in the course list
%		into an actual list.
% Format: name1(Course list,list of names)
%
name2([],A) :- A = [].
name2([_+X2+_|Xs], A) :-
	append([X2],B,A),
	name2(Xs,B).

% Predicate:	marks
% Description:	Helper prediate for courseAvg.
%		Puts all marks in the course list into
%		an actual list.
% Format: marks(Course list,list of marks)
%
marks([],A) :- A = [].
marks([_+_+Mark|Xs], A) :-
	append([Mark],B,A),
	marks(Xs,B).

% Predicate:	count
% Description:	This predicate was made by Margaret Lamb, as
%		per the summary page, I take no credit for it.
%		Counts the number of times an element appears
%		in a given list.
% Format:	count(element,List to search,Int of times element appears)
%
count(_Item,[],0).
count(Item,[X|Xs],Result) :-
	Item = X,
	count(Item,Xs,TailCount),
	Result is TailCount+1.
count(Item,[X|Xs],TailCount) :-
	Item \= X,
count(Item,Xs,TailCount).
