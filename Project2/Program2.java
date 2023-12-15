/*
 * Name: Fahim Imtiaz
 * EID: fmi89
 */


// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;
public class Program2 {

    /**
     * findMaximumLength()
     *
     * @param problem - contains the regions of the graph.
     * @return The sum of all of the edges of the MST.
     * @function Should track the edges in the MST using region.mst_neighbors and region.mst_weights
     * This function will not modify the mst_lists when run Gradescope if called in calculateDiameter()
     */
    public int findMaximumLength(Problem problem) {
        // TODO: implement this function

        ArrayList<Region> regions = problem.getRegions();
        int numRegions = regions.size();
        int[] parent = new int[numRegions];
        int[] key = new int[numRegions];
        boolean[] visited = new boolean[numRegions];
        int i=0;
        while(i<numRegions)
        {
            key[i]=Integer.MIN_VALUE;
            visited[i]=false;
            i++;
        }

        key[0] = 0;
         parent[0] = -1;

        int c=0;

        while(c<=numRegions-2)
        {
            int max = Integer.MIN_VALUE;
            int maxIndex = -1;

            for (int v = 0; v < numRegions; v++) {
                if (!visited[v] && key[v] > max) {
                    max = key[v];
                    maxIndex = v;
                }
            }
            int u= maxIndex;
            //int u=getMaxKey(key, visited, numRegions);
            visited[u]=true;
            Region current=regions.get(u);

            int v=0;
            while(v<current.getNeighbors().size())
            {
                Region neigh=current.getNeighbors().get(v);
                int w=current.getWeights().get(v);
                if(visited[neigh.getId()]==false)
                {
                    if(w>key[neigh.getId()])
                    {
                        parent[neigh.getId()]=u;
                        key[neigh.getId()]=w;
                    }
                }
                v++;
            }
            c++;

        }

        for (i = 1; i < numRegions; i++) {
            Region parentRegion = regions.get(parent[i]);
            Region currentRegion = regions.get(i);

            parentRegion.getMST_Neighbors().add(currentRegion);
            parentRegion.getMST_Weights().add(key[i]);

            currentRegion.getMST_Neighbors().add(parentRegion);
            currentRegion.getMST_Weights().add(key[i]);
        }



        int total=0;
        i=1;
        while(i<=numRegions-1)
        {
            total=total+key[i];
            i++;
        }

        return total;
    }



    /* calculateDiameter(Problem problem)
     *
     * @param problem  - contains the regions of the problem. Each region has an adjacency list
     * defined by mst_neighbors and mst_weights, which defines the provided MST. You should not modify the mst.
     *
     */
    public int calculateDiameter(Problem problem) {

        // Call findMaximumLength in your code to get a MST to test with. In gradescope, we will provide the mst in each regions mst_neighbors and mst_weights list
        // COMMENT THIS OUT BEFORE YOU SUBMIT TO GRADESCOPE
        int length = findMaximumLength(problem);   //uncomment this part for testing MST from previous class

        // TODO: Implement this function

        // Initialization
        ArrayList<Region> regions = problem.getRegions();
        int n = regions.size();
        if (n == 0) return 0;

        Region farthest = bfs(regions, regions.get(0));


        Region endOfDiameter = bfs(regions, farthest);


        return endOfDiameter.getMinDist();
    }

    private Region bfs(ArrayList<Region> allRegions, Region start) {
        int n = allRegions.size();
        boolean[] visited = new boolean[n];

        Queue<Region> queue = new LinkedList<>();

        for (Region r : allRegions) {

            r.resetMinDist();
        }

        start.setMinDist(0);

        queue.add(start);
        visited[start.getId()] = true;

        Region farthestNode = start;

        while (!queue.isEmpty()) {
            Region current = queue.poll();
            farthestNode = current;

            for (int i = 0; i < current.getMST_Neighbors().size(); i++) {
                Region neighbor = current.getMST_Neighbors().get(i);

                if (!visited[neighbor.getId()] && neighbor.getMinDist() == Integer.MAX_VALUE) {

                    visited[neighbor.getId()] = true;

                    neighbor.setMinDist(current.getMinDist() + 1);  // +1 since we want the number of edges
                    queue.add(neighbor);

                }
            }
        }

        //    for (Region region : regions) {
       //    region.resetMinDist();
       //}


        return farthestNode;
    }

}