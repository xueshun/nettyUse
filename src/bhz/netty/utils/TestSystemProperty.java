package bhz.netty.utils;

public class TestSystemProperty {
	public static void main(String[] args) {
		
		System.out.println("java�汾�ţ�"+ System.getProperty("java.version"));
		System.out.println("java�ṩ������:"+System.getProperty("java.vendor"));
		System.out.println("java�ṩ����վ"+System.getProperty("java.vendor.url"));
		System.out.println("jreĿ¼��"+System.getProperty("java.home"));
		System.out.println("java������淶�汾��" + System.getProperty("java.vm.specification.version"));
		System.out.println("java������淶�ṩ��" + System.getProperty("java.vm.specification.vendor"));
		System.out.println("Java������淶���ƣ�" + System.getProperty("java.vm.specification.name")); //
		System.out.println("Java������汾�ţ�" + System.getProperty("java.vm.version")); // 
		System.out.println("Java������ṩ�̣�" + System.getProperty("java.vm.vendor"));
		System.out.println("Java��������ƣ�" + System.getProperty("java.vm.name"));
		System.out.println("Java�淶�汾�ţ�" + System.getProperty("java.specification.version"));
		System.out.println("Java�淶�ṩ�̣�" + System.getProperty("java.specification.vendor"));
		System.out.println("Java�淶���ƣ�" + System.getProperty("java.specification.name"));
		System.out.println("Java��汾�ţ�" + System.getProperty("java.class.version"));
		System.out.println("Java��·����" + System.getProperty("java.class.path"));
		System.out.println("Java lib·����" + System.getProperty("java.library.path"));
		System.out.println("Java���������ʱ·����" + System.getProperty("java.io.tmpdir"));
		System.out.println("Java��������" + System.getProperty("java.compiler"));
		System.out.println("Javaִ��·����" + System.getProperty("java.ext.dirs"));
		System.out.println("����ϵͳ���ƣ�" + System.getProperty("os.name"));
		System.out.println("����ϵͳ�ļܹ���" + System.getProperty("os.arch"));
		System.out.println("����ϵͳ�汾�ţ�" + System.getProperty("os.version"));
		System.out.println("�ļ��ָ�����" + System.getProperty("file.separator"));
		System.out.println("·���ָ�����" + System.getProperty("path.separator")); 
		System.out.println("ֱ�߷ָ�����" + System.getProperty("line.separator"));
		System.out.println("����ϵͳ�û�����" + System.getProperty("user.name"));
		System.out.println("����ϵͳ�û�����Ŀ¼��" + System.getProperty("user.home"));
		System.out.println("��ǰ��������Ŀ¼��" + System.getProperty("user.dir"));
	}
}
