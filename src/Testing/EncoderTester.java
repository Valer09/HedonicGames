package Testing;

import java.util.ArrayList;
import java.util.List;

public class EncoderTester {
    static int n,k,N=0;
    public static void main(String [] args){
        n=6;
        k=3;
        N=11;

        long positionlists[];
        long nPosition=binomialCoeff(n,k);
        positionlists = new long[(int)nPosition];

        Long [] Ci = getSet(N);
        for ( Long l : Ci)
            System.out.println(l+", ");

    }

    public static Long [] getSet(long position){
        Long [] Ci;
        Ci = new Long[k];
        int n=N;

        for (int i=k; i > 0; i--){
            Ci[i-1] = getCi(i, n) ;
            n= (int) (n-binomialCoeff(  (Ci[i-1]).intValue() , i));
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
        static long binomialCoeff(int n, int k)
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