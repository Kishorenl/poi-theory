import javax.lang.model.util.ElementScanner6;

class kmp{

    public int[] buildKMP(String str)
    {
        int prefixArray = new int[str.length()];
        prefixArray[0] = 0;
        int j=1;
        for(int i=0; i < str.length(); i++){
            if(str.charAt(i) == str.charAt(j))
            {
                prefixArray[i] = j++;
            }
            else if(j > 0)
            {
                j--;
            }
            else{
                prefixArray[i] = 0;
                j=0;
            }
        }
    }

    public void checkSubString(String str, String subString)
    {
        int[] prefixArray = buildKMP(subString);

        int j =  prefixArray[0];
        for(int i=0; i < str.length(); i++){
            if(str.charAt(i) == subString.charAt(j))
            {
                if(j== subString.length())
                {
                    System.out.println("Found!");
                    continue;
                }
                j++;
            }
            else if(j > 0)
            {
                j--;
            }
            else{
                
                j=0;
            }
        }
    }

    public static void main(String args[]){
        String str = "ABCDEFG";
        String subString = "DEF";

        checkSubString(str, subString);
    }

    // Robin karp 
    int prime =  199;
    public  int hashCode(String str, int start, int end)
    {
        int hashCode = 0;

        for(int i = start; i < end; i++)
        {
            hashCode += (str.charAt(i) - 'a')*Math.pow(prime, i);
        }

        return hashCode;
    }


    public boolean checkEquals(String str1, String str2)
    {
        return str1.equals(str2);
    }

    public void robinKarp(String str, String  subString)
    {
        int subStringHashCode = hashCode(subString, 0, subString.length());
        int initialHashCode = hashCode(str, 0, subString.length());
        int newHashCode = 0;
        if(initialHashCode == subStringHashCode  && checkEquals(str, subString))
        {
            System.out.println("Found here at 0");
        }
        for(int i = 1; i < str.length(); i++)
        {
            initialHashCode = (initialHashCode  -  (str.charAt(i) - 'a') )/prime;
            initialHashCode += (str.charAt(i+subString.length()) - 'a')   * Math.pow(prime, subString.length()-1);
            if(initialHashCode == subStringHashCode)
            {
                return checkEquals(str.substring(i, i+subString.length()), subString);
            }
        }
    }

}


class  Paliandrome{
    public boolean isPaliandrome(String  str)
    {
        // brute force
        int j =  str.length();
        int i=0;
        while(i < j)
        {
            if(str.charAt(i) != str.charAt(j))
            {
                return false;
            }
            else{
                i++;
                j--;
            }
        }
        return true;
    }
}

class subStringPaliandrome{
    public void subStringPaliandrome(String str)
    {
        int[][] arr = new int[str.length()][str.length()];
        
    }
}