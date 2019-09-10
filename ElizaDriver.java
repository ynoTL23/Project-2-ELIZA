package ElizaProject;

public class ElizaDriver {

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Eliza e = new Eliza();

			}
		});

	}

}
