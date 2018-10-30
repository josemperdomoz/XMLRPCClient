package rpcclient;


import java.net.URL;
import java.util.*;
import org.apache.xmlrpc.client.*;


public class Client {

    public static final String nodeEndpoint = "http://130.240.135.115:57103"; // getText()
    public static final String fakeOriginAddress = "ffffffffffffffff"; // hardcoded leave it as it is!
    public static ArrayList<Integer> generatedData;
    public static int X;


    public static void main(String[] args)
    {


        // Getting Dimensionality of X data
        // int n = 32; // Dimensionality of the Data (Fancy Word: Hyperdimensional Vector) we will retrieve it from the nodes!

        // Connecting to Node
        X = connectToNode(); // Wrapper for remote 'connect' method! I retrieve the width of the Data with this method as well!
        System.out.println("The width of the data is " + X + " bits size long!");
        generatedData = randomArrayList(X);
        ArrayList<Integer> generatedData2 =randomArrayList(X);
        java.util.List dataToStore = (List) generatedData ;
        java.util.List dataToStore2 = (List) generatedData2 ;
        System.out.println("Stored Data : " + dataToStore);

        Integer countStorageLocations;


        countStorageLocations =  storeData(dataToStore);
        System.out.println(" Data was stored in " + countStorageLocations + " different storage locations in the Hybrid P2P network!");
        searchAndBrush(generatedData);



        // Storing data 20 times
        //for(int i=0; i<20;i++)
        //{
        //countStorageLocations =  storeData(dataToStore); // wrapper for remote 'store' method!
        //}
        /*
        Integer countStorageLocations2 = null;
        // Storing other non-important data 10 times
        for(int i=0; i<10;i++)
        {
            countStorageLocations2 =  storeData(dataToStore2); // wrapper for remote 'store' method!
        }
        */

        //System.out.println(" Unimportant data was stored in " + countStorageLocations2 + " different storage locations in the network! ");

        /*
        // Printing Generated Data
        for(int i = 0; i < dataToStore.size(); i++)
        {
            System.out.print(dataToStore.get(i));
        }
        */


        /*
        // Retrieving Data
        Integer[] retrievedValue = retrieveData(dataToStore);
        for(int i = 0; i < retrievedValue.length; i++)
        {
            System.out.print(retrievedValue[i]);
            System.out.print(",");
        }

        System.out.println("");

        // Binarizing Retrieved!
        System.out.println("These is the retrieved binarized data: ");
        ArrayList<Integer> binaryData = binarizeData(retrievedValue);
        */
       /*
        for(int i = 0; i < binaryData.size(); i++)
        {

            System.out.print(binaryData.get(i));
        }

        // Comparing Data with Originial One!

        int hammingDistance = dataComparison(binaryData, generatedData); // Output Hamming Distance
        System.out.println("Current Hamming Distance between retrieved data and original equals to :  " + hammingDistance);


        */
        // search function()







    }






    public static ArrayList<Integer> randomArrayList(int n)
    {
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            list.add(random.nextInt(2));
        }
        return list;
    }


    public static Integer storeData(java.util.List dataToStore)
    {
        Integer count=null;
        try
        {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(nodeEndpoint));
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);
            Object[] params = new Object[]{ dataToStore, fakeOriginAddress};
            Object rawResponse = (Object) client.execute("store", params);
            count = (Integer) rawResponse;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

        return count;
    }

    public static int connectToNode()
    {
        Integer widthData = null;
        try
        {

            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(nodeEndpoint));
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);
            Object[] paramsEmpty = new Object[]{};
            Object rawResponse = (Object) client.execute("connect", paramsEmpty);
            widthData = (Integer) rawResponse;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        return widthData;
    }

    public static Integer[] retrieveData(java.util.List data)
    {
        Integer[] result = null;
        try
        {

            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(nodeEndpoint));
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);
            Object[] params = new Object[]{data, fakeOriginAddress};
            Object[] rawResponse = (Object[]) client.execute("retrieve", params);
            result = Arrays.asList(rawResponse).toArray(new Integer[0]);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

        return result;
    }

    public static ArrayList<Integer> binarizeData(Integer[] retrievedValue)
    {
        ArrayList<Integer> binarizedData = new ArrayList<>();
        for (int i = 0; i < retrievedValue.length; i++)
        {
            if(retrievedValue[i]>0)
                binarizedData.add(i,1);
            if(retrievedValue[i] < 0)
                binarizedData.add(i,0);
        }
        return binarizedData;
    }


    public static int dataComparison(ArrayList<Integer> binarizedData, ArrayList<Integer> dataToStore )
    {
        int hammingDistance = 0;
        for(int i = 0;i < binarizedData.size();i++)
        {
            if(!binarizedData.get(i).equals(dataToStore.get(i)))
            {
                hammingDistance++;
            }
        }
        return hammingDistance;

    }

    public static void searchAndBrush(ArrayList<Integer> currentData)
    {
        int numberOfIterations=1;
        ArrayList<Integer>  newBinaryData = new ArrayList<Integer>(currentData);
        boolean isSearching =true;
        while(isSearching)
        {
            System.out.println("Current Iteration " + numberOfIterations);
            java.util.List currentInputData = (List) newBinaryData;

            /*
            Integer currentStorageLocations =  storeData(currentInputData);
            System.out.println("Data was stored in  " + currentStorageLocations + " locations.");
            */
            Integer[] currentRetrievedData = retrieveData(currentInputData);
            System.out.println("Noisy data equals to: " );
            for(int i = 0; i < currentRetrievedData.length; i++)
            {
                System.out.print(currentRetrievedData[i]);
                System.out.print(" ");
            }
            System.out.println("");
            System.out.println("Binarized Data equals to: ");
            ArrayList<Integer> currentBinaryData = binarizeData(currentRetrievedData);
            for(int i = 0; i < currentBinaryData.size(); i++)
            {
                System.out.print(currentBinaryData.get(i));
            }
            int currentHammingDistance = dataComparison(currentBinaryData, generatedData);

            System.out.println("");
            System.out.println("Current Hamming Distance is : " + currentHammingDistance);
            System.out.println("");

            if(currentHammingDistance == 0 )
            {
                System.out.println("Voilà! Here is the clean version of the data you requested! " );
                for(int i = 0; i < currentBinaryData.size(); i++)
                {
                    System.out.print(currentBinaryData.get(i));
                }
                break;
            }
            if( numberOfIterations > 15 && currentHammingDistance >= X/2)
            {
                System.out.println("We couldn't find the data you requested! Désolè! Hamming Distance diverges to : " + currentHammingDistance);
                isSearching = false;
                break;
            }
            newBinaryData = new ArrayList<Integer>(currentBinaryData);
            numberOfIterations++;
        }
    }





}
