package Generators;

import Structures.Agent;

import java.util.ArrayList;
import java.util.Random;

public class Codifier {
    private static int n,k,N=0;

    public static Long [] decode(int i, int j, int pos){
    n=i;
    k=j;

    Long [] set = getSet(pos);

    return set;

    }
    static String toString(Long[] set){
        String s="";
        for (Long l : set)
            s=s+l+", ";
        return s;

    }

    public static Long [] getSet(long position){
        Long [] Ci;
        Ci = new Long[k];
        int n=(int)position;

        for (int i=k; i > 0; i--){
            Ci[i-1] = getCi(i, n)+1;
            n= (int) (n-binomialCoeff(  (Ci[i-1]).intValue()-1 , i));
        }

        return Ci;

    }

    public static long getCi(int k, int x){
        long res=0L;
        for (int i=0; i<=n; i++){
            if (binomialCoeff(i,k) <= x)
                res=i;
            else if (binomialCoeff(i,k) == 0)
                res=i;
            else break;
        }
        return res;
    }

    // Returns value of Binomial Coefficient C(n, k)
    public static long binomialCoeff(int n, int k)
    {
        int res = 1;
        if (n<k)
            return 0;
        if (n==k)
            return 1;

        // Since C(n, k) = C(n, n-k)
        if ( k > n - k )
            k = n - k;

        // Calculate value of [n * (n-1) *---* (n-k+1)] / [k * (k-1) *----* 1]
        for (int i = 0; i < k; ++i)
        {
            res *= (n - i);
            res /= (i + 1);
        }

        return res;
    }

}
