''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
'                                                                                                                '                                                            '
'   Description:  There are three functions in this file:                       '                                                                                    
'                 1. rectangular, which checks if an input list to see if it is in a rectangular                 '
'                    shape.                                                                                      '
'                 2. rotate, which rotates an input rectangular list 90 degrees clockwise.                       '
'                 3. opish, which translates an input string into Opish.                                         '
'                                                                                                                '
''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''


####
#rectangular function
#
#Takes a user input dimensional list and checks to see if the length and width of the array corresponds
#to that of a "rectangle". If the length of every list within the list is equal, the function returns True.
#If not, returns False.
#
#@param alist, the list that is checked.
#@return True or False
####

def rectangular(*alist):
    '''
    Finds the length, the length of the list within one argument, of the first argument in the user input dimensional
    list to compare to others with.
    '''
    length = len(alist[0])
    'Finds the width, or total number of arguments'
    width = len(alist)

    'Loops for the length of the list'
    for i in range (0,width):
        if length != len(alist[i]):
            '''
            If there is an argument in the list that does not have the same length as the first argument, it is
            not a rectangle, so return false.
            '''
            return False
        
    'All arguments in the list are of equal length (is a rectangle), return true.'
    return True

        
####
#rotate function
#
#Takes a "rectangular" user input dimensional list and rotates the rectangle 90 degrees clockwise,
#or rather, it "rotates" the values in the list 90 degrees, and then returns this array.
#
#@param alist, the list that is rotated.
#@return rotated, the complete rotated list.
####

def rotate (*alist):
    'Finds the width of the rotated list, the length of the list within one argument in the user input dimensional list'
    width = len(alist[0])
    'Finds the length of the rotated list, the total number of arguments in the user input dimensional list'
    length = len(alist)
    
    '''
    Creates a dimensional list, in which the list length is equal to the width, or the total number of arguments,
    in the given list from the user and the length of each list in the list is the length of given list.
    '''
    rotated = [[0]*length for y in range(width)]

    '''
    Fills in the list with the values of the input list "rotated by 90 degrees", or rather, filling in with values
    from the last argument to the first argument across the width of the list.
    '''
    for i in range (0,width):
        for j in range (0,length):
            rotated[i][j]=alist[width-j][i]
            
    'Returns the rotated list'
    return rotated


####
#opish function
#
#Takes a user input string and translates it into Opish, where after a consonant, or sequence of
#consonants, an "op" is placed after it. This function assumes that the input is all lower case.
#It outputs the translated string to the user.  
#
#@param somestring, a string that is to be translated.
#@return The translated string.
####

def opish (somestring):
    'Changes the user input string into a character array to make it easier to work with'
    chararray = list(somestring)
    'Counter to mark what number'
    arrayplace = 0;

    'Loops as long as the counter is less than the length of the given string'
    while (arrayplace < len(chararray)):

        if (ord(chararray[arrayplace]) != 97 and ord(chararray[arrayplace]) != 101 and \
        ord(chararray[arrayplace]) != 105 and ord(chararray[arrayplace]) != 111 and \
        ord(chararray[arrayplace]) != 117 and ord(chararray[arrayplace]) >=97 and ord(chararray[arrayplace]) <= 122):
            'If the character currently at this spot is not a vowel and is a lower case letter, then...'
            
            if (arrayplace == len(chararray)-1):
                '...Add op if it is the end of the word'
                chararray.append('o')
                chararray.append('p')
                'Increase counter by 3 to skip the letters that were just added to the list'
                arrayplace += 3
                
            elif (ord(chararray[arrayplace+1]) == 97 or ord(chararray[arrayplace+1]) == 101 or \
            ord(chararray[arrayplace+1]) == 105 or ord(chararray[arrayplace+1]) == 111 or \
            ord(chararray[arrayplace+1]) == 117 or ord(chararray[arrayplace+1]) < 97 or ord(chararray[arrayplace+1]) > 122):
                '...Add op if the next character is a vowel or not a lowercase consonant'
                chararray.insert(arrayplace+1,'o')
                chararray.insert(arrayplace+2,'p')
                'Increase counter by 3 to skip the letters that were just added to the list'
                arrayplace += 3
                
            else:
                'Else there are still consonants ahead of this letter, so just continue onwards'
                arrayplace += 1
                
        else:
           'Else the character at this spot is a vowel or is not a lower case letter, so skip it'
           arrayplace += 1

    'Returns the joined character array that has been translated to Opish'
    return ''.join(chararray)
            
