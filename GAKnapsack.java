import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GAKnapsack {
	private Random random = null; //�����������
	 
	private float[] weight = null; //��Ʒ����
	private float[] profit = null; //��Ʒ��ֵ
	private int len; // Ⱦɫ�峤��
 
	private float capacity; //��������
	private int scale; //��Ⱥ��ģ
	private int maxgen; //������
	private float irate; //�����ʣ����еĸ��嶼��Ҫ�໥����ģ�����Ľ�����ָ����ʱÿλ���淢������Ŀ����ԣ�
	private float arate1; //�����ʣ�ĳ�����巢������Ŀ����ԣ�
	private float arate2; //����ȷ����������ĸ���ÿλ��������Ŀ�����
	private File data = null; //��Ʒ��������Ʒ��ֵ�������ļ�
 
	private boolean[][] population = null; //��һ����Ⱥ
	private float[] fitness = null; //��Ⱥ����Ӧ��
 
	private float bestFitness; //���Ÿ���ļ�ֵ
	private boolean[] bestUnit = null; //���Ÿ������Ʒȡ�����
 
	class SortFitness implements Comparable<SortFitness>{
		int index;
		float fitness;
		public int compareTo(SortFitness c) {
			float cfitness = c.fitness;
			if(fitness > cfitness) {
				return -1;
			} else if(fitness < cfitness) {
				return 1;
			} else {
				return 0;
			}
		}
	}
 
	/**
	 * @param capacity : ��������
	 * @param scale �� ��Ⱥ��ģ
	 * @param maxgen �� ������
	 * @param irate �� �����ʣ����еĸ��嶼��Ҫ�໥����ģ�����Ľ�����ָ����ʱÿλ���淢������Ŀ����ԣ�
	 * @param arate1 �������ʣ�ĳ�����巢������Ŀ����ԣ�
	 * @param arate2 ������ȷ����������ĸ���ÿλ��������Ŀ�����
	 * @param file : ��Ʒ��������Ʒ��ֵ�������ļ�
	 */
	public GAKnapsack(float capacity, int scale, int maxgen, float irate, float arate1, float arate2, File data) {
		this.capacity = capacity;
		this.scale = scale;
		this.maxgen = maxgen;
		this.irate = irate;
		this.arate1 = arate1;
		this.arate2 = arate2;
		this.data = data;
		random = new Random(System.currentTimeMillis());
	}
 
	//��ȡ��Ʒ��������Ʒ��ֵ����
	private void readDate() {
		List<Object> tmp = Reader.read(data);
		weight = (float[])tmp.get(0);
		profit = (float[])tmp.get(1);
		len = weight.length;
	}
 
	//��ʼ����ʼ��Ⱥ
	private void initPopulation() {
		fitness = new float[scale];
		population = new boolean[scale][len];
		//���ǵ�������ɵĳ�ʼ����ȺЧ���п��ܲ��ã����������Ⱥ�ĳ�ʼ������һ�����Ż�
		//����ÿһ�����壬�����һ������ֵ��0.5 capacity �� 1.5 capacity��
		//Ȼ�������Ӧ����Ʒ���ø����У�ֱ�������������������
		for(int i = 0; i < scale; i++) {
			float tmp = (float)(0.5 + Math.random()) * capacity;
			int count = 0; //��ֹ��ʼ���ķ�̫�������Դ
			for(int j = 0; j < tmp;) {
				int k = random.nextInt(len);
				if(population[i][k]) {
					if(count == 3) {
						break;
					}
					count++;
					continue;
				} else {
					population[i][k] = true;
					j += weight[k];
					count = 0;
				}
			}
		}
	}
 
	//����һ���������Ӧ��
	private float evaluate(boolean[] unit) {
		float profitSum = 0;
		float weightSum = 0;
		for (int i = 0; i < unit.length; i++) {
			if (unit[i]) {
				weightSum += weight[i];
				profitSum += profit[i];
			}
		}
		if (weightSum > capacity) {
			//�ø���Ķ�Ӧ��������Ʒ�����������˱���������
			return 0;
		} else {
			return profitSum;
		}
	}
 
	//������Ⱥ���и������Ӧ��
	private void calcFitness() {
		for(int i = 0; i < scale; i++) {
			fitness[i] = evaluate(population[i]);
		}
	}
 
	//��¼���Ÿ���
	private void recBest(int gen) {
		for(int i = 0; i < scale; i++) {
			if(fitness[i] > bestFitness) {
				bestFitness = fitness[i];
				bestUnit = new boolean[len];
				for(int j = 0; j < len; j++) {
					bestUnit[j] = population[i][j];
				}
			}
		}
	}
 
	//��Ⱥ����ѡ��
	//ѡ����ԣ���Ӧ��ǰ10%�ĸ��������һ��ѭ���У�Ȼ���ڣ��������10%�ĸ��� + ʣ�µ�90%���壩�����ȡ90%����
	private void select() {
		SortFitness[] sortFitness = new SortFitness[scale];
		for(int i = 0; i < scale; i++) {
			sortFitness[i] = new SortFitness();
			sortFitness[i].index = i;
			sortFitness[i].fitness = fitness[i];
		}
		Arrays.sort(sortFitness);
 
		boolean[][] tmpPopulation = new boolean[scale][len];
 
		//����ǰ10%�ĸ���
		int reserve = (int)(scale * 0.1);
		for(int i = 0; i < reserve; i++) {
			for(int j = 0; j < len; j++) {
				tmpPopulation[i][j] = population[sortFitness[i].index][j];
			}
			//�������ĸ��������
			for(int j = 0; j < len; j++) {
				population[sortFitness[i].index][j] = false;
			}
			float tmpc = (float)(0.5 + Math.random()) * capacity;
			int count = 0;
			for(int j = 0; j < tmpc;) {
				int k = random.nextInt(len);
				if(population[sortFitness[i].index][k]) {
					if(count == 3) {
						break;
					}
					count++;
					continue;
				} else {
					population[sortFitness[i].index][k] = true;
					j += weight[k];
					count = 0;
				}
			}//
		}
 
		//�����90%�ĸ������
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < scale; i++) {
			list.add(i);
		}
		for(int i = reserve; i < scale; i++) {
			int selectid = list.remove((int)(list.size()*Math.random()));
			for(int j = 0; j < len; j++) {
				tmpPopulation[i][j] = population[selectid][j];
			}
		}
		population = tmpPopulation;
	}
 
	//���н���
	private void intersect() {
		for(int i = 0; i < scale; i = i + 2)
			for(int j = 0; j < len; j++) {
				if(Math.random() < irate) {
					boolean tmp = population[i][j];
					population[i][j] = population[i + 1][j];
					population[i + 1][j] = tmp;
				}
			}
	}
 
	//����
	private void aberra() {
		for(int i = 0; i < scale; i++) {
			if(Math.random() > arate1) {
				continue;
			}
			for(int j = 0; j < len; j++) {
				if(Math.random() < arate2) {
					population[i][j] = Math.random() > 0.5 ? true : false;
				}
			}
		}
	}
	
	//�Ŵ��㷨
	public void solve() {
		readDate();
		initPopulation();
		for(int i = 0; i < maxgen; i++) {
			//������Ⱥ��Ӧ��ֵ
			calcFitness();
			//��¼���Ÿ���
			recBest(i);
			//������Ⱥѡ��
			select();
			//���н���
			intersect();
			//��������
			aberra();
		}
		
		int totalWeight = 0; 
		for(int i = 0; i < bestUnit.length; i++) {
			if(bestUnit[i]){
				totalWeight += weight[i];
			}
		}
		System.out.println("total profit:" + bestFitness);
		System.out.println("total weight:" + totalWeight);
	}
	
	public static void main(String[] args) {
		File data = new File(".//data//data1.txt");
		//��������
		//��Ⱥ��ģ
		//������
		//�����ʣ����еĸ��嶼��Ҫ�໥����ģ�����Ľ�����ָ����ʱÿλ���淢������Ŀ����ԣ�
		//�����ʣ�ĳ�����巢������Ŀ����ԣ�
		//����ȷ����������ĸ���ÿλ��������Ŀ�����
		//��Ʒ��������Ʒ��ֵ�������ļ�
		GAKnapsack gaKnapsack = new GAKnapsack(1000, 200, 2000, 0.5f, 0.05f, 0.1f, data);
		gaKnapsack.solve();
	}

}
