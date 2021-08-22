import arrayList.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        Integer[] integers = new Integer[]{1,2,3};
        integerArrayList.add(2);
        integerArrayList.add(3);
        integerArrayList.add(4);
        System.out.println("first:");
        for(Integer integer : integerArrayList){
            System.out.println(integer);
        }

        integerArrayList.remove((Integer) 3);
        System.out.println("second:");
        for(Integer integer : integerArrayList){
            System.out.println(integer);
        }

        integerArrayList.set(1, 100);
        System.out.println("third:" + integerArrayList.size());
        for(Integer integer : integerArrayList){
            System.out.println(integer);
        }

        System.out.println("third.1 contains 2:" + integerArrayList.contains(2));
        System.out.println("third.1 contains 3:" + integerArrayList.contains(3));


        ArrayList<Integer> integerArrayList1 = new ArrayList<>();
        integerArrayList1.add(1);
        integerArrayList1.add(100);
        integerArrayList1.add(7);
        integerArrayList.removeAll(integerArrayList1);
        System.out.println("forth:" + integerArrayList.size());
        for(Integer integer : integerArrayList){
            System.out.println(integer);
        }
    }
}
