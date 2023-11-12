/*
 * Name: <Fahim Imtiaz>
 * EID: <fmi89>
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {


    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) {
        /* TODO implement this function */
        /* TODO implement this function */
        int m = problem.getUniversityCount();
        int n = problem.getStudentCount();
        ArrayList<Integer> studentMatching = problem.getStudentMatching();
        ArrayList<ArrayList<Integer>> universityPreference = problem.getUniversityPreference();
        ArrayList<ArrayList<Integer>> studentPreference = problem.getStudentPreference();
        int i=0;
        while(i<n)
        {
            int matcuniv= studentMatching.get(i);
            int size=studentPreference.get(i).size();
            int[] prefUniv=new int[size];
            for(int j=0;j<size;j++)
            {
                prefUniv[j]=studentPreference.get(i).get(j);
            }
            int k=0;
            while(k<size)
            {
                if(prefUniv[k]==matcuniv)
                {
                    break;
                }
                int size1=universityPreference.get(prefUniv[k]).size();
                int[] prefstud=new int[size1];
                for(int t=0;t<size1;t++)
                {
                    prefstud[t]=universityPreference.get(prefUniv[k]).get(t);
                }

                int availpos=problem.getUniversityPositions().get(prefUniv[k]);
                int counter=0;

                int a=0;
                while(a<size1)
                {
                    if(prefstud[a]==i)
                    {
                        return false;
                    }
                    if(studentMatching.get(prefstud[a])==prefUniv[k])
                    {
                        counter+=1;
                        if(counter>=availpos)
                        {
                            break;
                        }
                    }
                    a++;
                }
                k++;
            }
            i++;
        }
        return true;
    }


    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_universityoptimal(Matching problem) {
        /* TODO implement this function */
        /* TODO implement this function */
        int m = problem.getUniversityCount();
        int n = problem.getStudentCount();
        ArrayList<Integer> universityPositions = problem.getUniversityPositions();
        ArrayList<ArrayList<Integer>> universityPreference = problem.getUniversityPreference();
        //ArrayList<Integer> studentMatching = new ArrayList<>();

//        // Initialize 'nextToPropose' array to keep track of the next student each university will propose to
        int[] nextpropose=new int[m];
        for(int i=0;i<m;i++)
        {
            nextpropose[i]=0;
        }

        int[] studentmatch=new int[n];
        for(int i=0;i<n;i++)
        {
            studentmatch[i]=-1;
        }

        LinkedList<Integer> freeuniv=new LinkedList<>();
        for(int i=0;i<m;i++)
        {
            freeuniv.add(i);
        }

        for(;!freeuniv.isEmpty();)
        {
            int univ=freeuniv.poll();
            int size=universityPreference.get(univ).size();
            int[] pref=new int[size];

            for(int i=0;i<size;i++)
            {
                pref[i]=universityPreference.get(univ).get(i);
            }

            int availpos=universityPositions.get(univ);

            for(; availpos>0 && nextpropose[univ]<n ;)
            {
                int stud=pref[nextpropose[univ]];
                nextpropose[univ]+=1;

                if(studentmatch[stud]==-1)
                {
                    studentmatch[stud]=univ;
                    availpos-=1;
                }
                else
                {
                    int matchuniv=studentmatch[stud];
                    int size1=problem.getStudentPreference().get(stud).size();
                    int[] studpref=new int[size1];
                    for(int i=0;i<size1;i++)
                    {
                        studpref[i]=problem.getStudentPreference().get(stud).get(i);
                    }
                    int indexuniv=-1;
                    int indexmatchuniv=-1;

                    int i=0;
                    while(i<size1)
                    {
                        if(studpref[i]==univ)
                        {
                            indexuniv=i;
                        }
                        if(studpref[i]==matchuniv)
                        {
                            indexmatchuniv=i;
                        }
                        i++;
                    }
                    if(indexuniv<indexmatchuniv)
                    {
                        studentmatch[stud]=univ;
                        availpos-=1;
                        universityPositions.set(matchuniv, universityPositions.get(matchuniv)+1);
                        freeuniv.add(matchuniv);
                    }
                }
            }

            universityPositions.set(univ, availpos);

        }
        ArrayList<Integer> smatch=new ArrayList<>();
        for(int i=0;i<studentmatch.length;i++)
        {
            smatch.add(studentmatch[i]);
        }

        return new Matching(problem, smatch);
    }


    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_studentoptimal(Matching problem) {
        /* TODO implement this function */
        int m = problem.getUniversityCount();
        int n = problem.getStudentCount();
        ArrayList<ArrayList<Integer>> studentPreference = problem.getStudentPreference();
        ArrayList<Integer> studentMatching = new ArrayList<>();

        int[] studentmatch=new int[n];
        for(int i=0;i<n;i++)
        {
            studentmatch[i]=-1;
        }

        LinkedList<Integer> freestud=new LinkedList<>();
        for(int i=0;i<n;i++)
        {
            freestud.add(i);
        }

        for(;!freestud.isEmpty();)
        {
            int stud=freestud.poll();
            int size=studentPreference.get(stud).size();
            int[] pref=new int[size];

            for(int i=0;i<size;i++)
            {
                pref[i]=studentPreference.get(stud).get(i);
            }

            for(int j:pref)
            {
                int availpos=problem.getUniversityPositions().get(j);
                if(availpos>0)
                {
                    studentmatch[stud]=j;
                    problem.getUniversityPositions().set(j, availpos - 1);
                    break;

                }
                else
                {
                    int size1=problem.getUniversityPreference().get(j).size();
                    int[] univpref=new int[size1];
                    for(int i=0;i<size1;i++)
                    {
                        univpref[i]=problem.getUniversityPreference().get(j).get(i);

                    }
                    int worst=-1;
                    int i=size1-1;
                    for(;i>=0;i--)
                    {
                        if(studentmatch[univpref[i]]==j)
                        {
                            worst=i;
                            break;
                        }

                    }

                    int curr=-1;
                    i=0;
                    for(;i<size1;i++)
                    {
                        if(univpref[i]==stud)
                        {
                            curr=i;
                            break;
                        }

                    }
                    if(curr<worst)
                    {
                        int worststud=univpref[worst];
                        studentmatch[worststud]=-1;
                        studentmatch[stud]=j;
                        freestud.add(worststud);
                        break;
                    }
                }
            }
        }
        ArrayList<Integer> smatch=new ArrayList<>();
        for(int i=0;i<studentmatch.length;i++)
        {
            smatch.add(studentmatch[i]);
        }

        return new Matching(problem, smatch);
    }

//
}

