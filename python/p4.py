'
'                                                                                                                                                                                    '
'   Description:  Functions for a Binary search tree, sorted by student ID number.
'                 Each node contains the student ID number, the name of the student, and
'                 the mark obtained by said student. 
'                                                                                                               
'


# Adds student ID, name, and mark to a binary search tree.
# BST is organized by ID number.
def add(tree, idnum, name, mark):
    
    if tree == None:
        newNode = {'id':idnum,'name':name,'mark':mark,'left':None,'right':None}
        return newNode
    
    elif tree['id'] == idnum:
        return tree
    
    elif idnum < tree['id']:
        tree['left'] = add(tree['left'], idnum, name, mark)
        return tree
    
    else: # idnum > tree['id']
        tree['right'] = add(tree['right'],idnum, name, mark)
        return tree       


# Searches the BST for an ID number, returns the name and mark in a 2-element list.
def search(tree, idnum):
    
    if tree == None:
        return None
    
    elif tree['id'] == idnum:
        return [tree['name'],tree['mark']]
    
    elif idnum < tree['id']:
        return search(tree['left'],idnum)
    
    else:
        return search(tree['right'],idnum)


# Prints an indented display of the BST.
def display(tree, indent=0):
    
    if tree == None:
        return
    
    else:
        display(tree['right'],indent+4)
        print ' '*indent + str(tree['id'])+' ('+str(tree['name'])+", "+ str(tree['mark'])+")"
        display(tree['left'],indent+4)


# Deletes value from BST given an ID number.
def delete(tree, idnum):
    
    if tree == None:
        # empty tree (so value is not in tree)
        return None
    
    elif idnum < tree['id']:
        # value to delete is in left subtree
        tree['left'] = delete(tree['left'],idnum)
        return tree
        
    elif idnum > tree['id']:
        # value to delete is in right subtree
        tree['right'] = delete(tree['right'],idnum)
        return tree

    
    else:
        # value to delete is in the root

        if tree['left'] == None and tree['right'] == None:
            # no subtrees -- root is the only node in the tree
            return None
      
        elif tree['left'] == None:
            # root has a right subtree, but no left subtree
            return tree['right']
        
        elif tree['right'] == None:
            # root has a left subtree, but no right subtree
            return tree['left']
        
        else:
            # The hard case: root has two subtrees.  Replace the
            # root value with the largest value from the left
            # subtree.
            newRoot = maxValue(tree['left'])
            tree['id'] = newRoot[0]
            tree['name'] = newRoot[1]
            tree['mark'] = newRoot[2]
            tree['left'] = delete(tree['left'], newRoot[0])
            return tree


# Helper function for delete function.
def maxValue(tree):
    """
    Returns the largest value in a non-empty tree.
    Does not change the tree.
    """
    if tree['right'] == None:
        # If there's no right subtree, the largest
        # value is in the root
        return [tree['id'],tree['name'],tree['mark']]
    
    else:
        # If there's a right subtree, the largest value is in there.
        return maxValue(tree['right'])


# Prints a multi-line display of the BST.
# Really though, this only prints the column labels.
# Helper function listree does the actual printing.
def report(tree):
    dup = tree
    print format('ID', '<8') + format('NAME', '<13') + 'MARK'
    listree(dup)


# Recursive helper function for report.
# Prints a multi-line display of the BST.
def listree(tree):
    
    if tree == None:
        # No value, no printing.
        return
    
    else:  
        if tree['left'] != None and tree['right'] == None:
            # All left ID numbers in the BST are smaller than the current number.
            # As such, print the current number after printing the smaller ones.
            listree(tree['left'])
            print format(tree['id'], '<8') + format(tree['name'], '<13') + str(tree['mark'])
            
        elif tree['right'] != None and tree['left'] == None:
            # All right ID numbers in the BST are larger than the current number.
            # As such, print the current number before printing the larger ones.
            print format(tree['id'], '<8') + format(tree['name'], '<13') + str(tree['mark'])
            listree(tree['right'])
            
        else: # (tree['left'] == None and tree['right'] == None) or (tree['left'] != None and tree['right'] != None)
            listree(tree['left'])
            print format(tree['id'], '<8') + format(tree['name'], '<13') + str(tree['mark'])
            listree(tree['right'])

# Searches the BST given a student name.
# Returns the name and mark in a 2-element list.
def findName(tree, name):
    
    if tree == None:
        # Empty tree/name is not in tree
        return None
    
    elif tree['name'] == name:
        return tree['id']
    
    else:
        
        if findName(tree['left'],name) != None:
            return findName(tree['left'],name)
        
        elif findName(tree['right'],name) != None:
            return findName(tree['right'],name)
        
        else:
            return None

# "Bell curve" function.
# Adds or subtracts a set number of points from all marks in the BST.
def curve(tree, points):
    
    if tree == None:
        return None
    
    else:
        tree['mark'] += points
        
        if tree['mark'] > 100:
            tree['mark'] = 100
            
        elif tree['mark'] < 0:
            tree['mark'] = 0
            
        curve(tree['left'],points)
        curve(tree['right'],points)




