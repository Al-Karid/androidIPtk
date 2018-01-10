package com.esatic.grenciss.androidiptk.classes;

/**
 * Created by grenciss on 1/10/18.
 */

/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org/>
 */
// package vlsmcalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Burhanuddin
 */
public class VLSM {
    public List<Subnet> out;
    /*public static void main(String[] args) {
        String majorNetwork = "192.168.10.20/24";
        Map<String, Integer> subnets = new HashMap<>(); // [name: size]
        subnets.put("A", 50);
        subnets.put("B", 30);
        subnets.put("C", 62);
        subnets.put("D", 10);

        List<Subnet> output = calcVLSM(majorNetwork, subnets);

        for (Subnet subnet : output) {
            System.out.println(subnet.name + "\t" +
                    subnet.neededSize + "\t" +
                    subnet.allocatedSize + "\t" +
                    subnet.address + "\t" +
                    subnet.mask + "\t" +
                    subnet.decMask + "\t" +
                    subnet.range + "\t" +
                    subnet.broadcast);
        }
    }*/

    public VLSM(String majorN, Map<String,Integer> subnets)
    {
        this.out = calcVLSM(majorN,subnets);
    }

    /**
     * Calculate VLSM.
     *
     * @param majorNetwork Major network
     * @param sortedSubnets A map of required subnets
     * @return A list of output subnets
     */
    public static List<Subnet> calcVLSM(String majorNetwork, Map<String, Integer> sortedSubnets) {
//        Map<String, Integer> sortedSubnets = sortMap(subnets);
        List<Subnet> output = new ArrayList<>();
        int currentIp = findFirstIp(majorNetwork);

        for (String key : sortedSubnets.keySet()) {  // for all subnets
            Subnet subnet = new Subnet();

            subnet.name = key;
            subnet.address = convertIpToQuartet(currentIp);

            int neededSize = sortedSubnets.get(key);
            subnet.neededSize = neededSize;

            int mask = calcMask(neededSize);
            subnet.mask = "/" + mask;
            subnet.decMask = toDecMask(mask);

            int allocatedSize = findUsableHosts(mask);
            subnet.allocatedSize = allocatedSize;
            subnet.broadcast = convertIpToQuartet(currentIp + allocatedSize + 1);

            String firstUsableHost = convertIpToQuartet(currentIp + 1);
            String lastUsableHost = convertIpToQuartet(currentIp + allocatedSize);
            subnet.range = firstUsableHost + " - " + lastUsableHost;

            output.add(subnet);

            currentIp += allocatedSize + 2;
        }

        return output;
    }

    /**
     * Sort map according to descending order of values.
     *
     * @param map Map
     * @return Sorted Map
     */
    private static Map<String, Integer> sortMap(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());  // descending
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    /**
     * Merge IP address quartet into a single binary string.
     * <p/>
     * Example: <code>192.168.0.1</code> to <code>11000000101010000000000000000001</code>
     *
     * @param ipAddress IP address
     * @return A single integer that stores IP address binary string
     */
    private static int convertQuartetToBinaryString(String ipAddress) {
        String[] ip = ipAddress.split("\\.|/");

        int octet1 = Integer.parseInt(ip[0]);
        int octet2 = Integer.parseInt(ip[1]);
        int octet3 = Integer.parseInt(ip[2]);
        int octet4 = Integer.parseInt(ip[3]);

        int output = octet1;
        output = (output << 8) + octet2;
        output = (output << 8) + octet3;
        output = (output << 8) + octet4;

        return output;
    }

    /**
     * Separate IP address binary string into quartet.
     * <p/>
     * Example: <code>11000000101010000000000000000001</code> to <code>192.168.0.1</code>
     *
     * @param ipAddress IP address binary string
     * @return A string of IP address in the form of quartet
     */
    private static String convertIpToQuartet(int ipAddress) {
        int octet1 = (ipAddress >> 24) & 255;
        int octet2 = (ipAddress >> 16) & 255;
        int octet3 = (ipAddress >> 8) & 255;
        int octet4 = ipAddress & 255;

        return octet1 + "." + octet2 + "." + octet3 + "." + octet4;
    }

    /**
     * Find the first IP address for the specified network.
     *
     * @param majorNetwork Major network IP
     * @return The first IP address
     */
    private static int findFirstIp(String majorNetwork) {
        String[] ip = majorNetwork.split("/");
        int mask = Integer.parseInt(ip[1]); // parse CIDR mask
        int offset = Integer.SIZE - mask;

        int majorAddress = convertQuartetToBinaryString(majorNetwork);
        int firstIp = (majorAddress >> offset) << offset;

        return firstIp;
    }

    /**
     * Calculate mask and return it in CIDR notation.
     *
     * @param neededSize The size for subnet
     * @return Mask
     */
    private static int calcMask(int neededSize) {
        int highestBit = Integer.highestOneBit(neededSize);
        int position = (int) (Math.log(highestBit) / Math.log(2));
        return Integer.SIZE - (position + 1);   // +1 because position starts with 0
    }

    /**
     * Find the total number of usable IP address/hosts.
     *
     * @param mask Mask
     * @return Total number of hosts
     */
    private static int findUsableHosts(int mask) {
        return (int) Math.pow(2, Integer.SIZE - mask) - 2;
    }

    /**
     * Convert mask from CIDR notation to quartet form.
     * <p/>
     * Example: <code>'/24'</code> to <code>'255.255.255.0'</code>
     *
     * @param mask Mask in CIDR notation
     * @return Mask in quartet form
     */
    private static String toDecMask(int mask) {
        if (mask == 0) {
            return "0.0.0.0";
        }
        int allOne = -1;    // '255.255.255.255'
        int shifted = allOne << (Integer.SIZE - mask);

        return convertIpToQuartet(shifted);
    }
}
