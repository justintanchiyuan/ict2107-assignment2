package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import javax.swing.JScrollPane;

public class GUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JScrollPane scrollPane = new JScrollPane();
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JButton btnTask = new JButton("Task 5");
		btnTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					task5.AirlineNegativeSentiments.main(null);
					readOutput("What is the median value of the trusting point in each airline?");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;
			}

			private void readOutput(String title) {
				try {
					ProcessBuilder builder = new ProcessBuilder("/usr/local/hadoop-2.6.0/bin/hadoop", "fs", "-cat",
							"/user/phamvanvung/airline/output/part-r-00000");
					builder.redirectErrorStream(true);
					Process process = builder.start();
					InputStream is = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					String line = null;
					String output = "";
					output += title + "\n\n";
					while ((line = reader.readLine()) != null) {
						output += line + "\n";
						System.out.println(line);
					}

					textArea.setText(output);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		JButton btnTask_1 = new JButton("Task 6");
		btnTask_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					task6.AirlineNegativeSentiments.main(null);
					readOutput(
							"How many delayed flights? Tweets can be filtered to only those with certain keywords (i.e., “delayed”) or hashtags (i.e., “#SFO”) to find a set of relevant messages");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;
			}

			private void readOutput(String title) {
				try {
					ProcessBuilder builder = new ProcessBuilder("/usr/local/hadoop-2.6.0/bin/hadoop", "fs", "-cat",
							"/user/phamvanvung/airline/output/part-r-00000");
					builder.redirectErrorStream(true);
					Process process = builder.start();
					InputStream is = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					String line = null;
					String output = "";
					output += title + "\n\n";
					while ((line = reader.readLine()) != null) {
						output += line + "\n";
						System.out.println(line);
					}

					textArea.setText(output);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnTask_2 = new JButton("Task 7");
		btnTask_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					task7.Task7Analysis.main(null);
					readOutput("How many unique IPs in the twitter dataset? Please print the IPs and the number of tweets from them.");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;

			}

			private void readOutput(String title) {
				try {
					ProcessBuilder builder = new ProcessBuilder("/usr/local/hadoop-2.6.0/bin/hadoop", "fs", "-cat",
							"/user/phamvanvung/airline/output/part-r-00000");
					builder.redirectErrorStream(true);
					Process process = builder.start();
					InputStream is = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					String line = null;
					String output = "";
					output += title + "\n\n";
					while ((line = reader.readLine()) != null) {
						output += line + "\n";
						System.out.println(line);
					}

					textArea.setText(output);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnTask_3 = new JButton("Task 1");
		btnTask_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					task1.TaskOneDriver.main(null);
					readOutput("How many negative reasons? (e.g., \"late flight\" or \"rude service\").");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;

			}

			private void readOutput(String title) {
				try {
					ProcessBuilder builder = new ProcessBuilder("/usr/local/hadoop-2.6.0/bin/hadoop", "fs", "-cat",
							"/user/phamvanvung/airline/output/part-r-00000");
					builder.redirectErrorStream(true);
					Process process = builder.start();
					InputStream is = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					String line = null;
					String output = "";
					output += title + "\n\n";
					while ((line = reader.readLine()) != null) {
						output += line + "\n";
						System.out.println(line);
					}

					textArea.setText(output);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnTask_4 = new JButton("Task 2");
		btnTask_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					task2.Task2.main(null);
					readOutput("Discover the people from which country complain the most for the airline services?");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;

			}

			private void readOutput(String title) {
				try {
					ProcessBuilder builder = new ProcessBuilder("/usr/local/hadoop-2.6.0/bin/hadoop", "fs", "-cat",
							"/user/phamvanvung/airline/output/part-r-00000");
					builder.redirectErrorStream(true);
					Process process = builder.start();
					InputStream is = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					String line = null;
					String output = "";
					output += title + "\n\n";
					while ((line = reader.readLine()) != null) {
						output += line + "\n";
						System.out.println(line);
					}

					textArea.setText(output);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnTask_5 = new JButton("Task 3");
		btnTask_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					task3.Task3.main(null);
					readOutput("How many people give the negative comments on the airline by giving the “badflight” or “CSProblem” negative reason for each country?");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;

			}

			private void readOutput(String title) {
				try {
					ProcessBuilder builder = new ProcessBuilder("/usr/local/hadoop-2.6.0/bin/hadoop", "fs", "-cat",
							"/user/phamvanvung/airline/output/part-r-00000");
					builder.redirectErrorStream(true);
					Process process = builder.start();
					InputStream is = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					String line = null;
					String output = "";
					output += title + "\n\n";
					while ((line = reader.readLine()) != null) {
						output += line + "\n";
						System.out.println(line);
					}

					textArea.setText(output);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnTask_6 = new JButton("Task 4");
		btnTask_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					task4.TaskFourDriver.main(null);
					readOutput("What are the top 3 airlines getting the most number of positive tweets?");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;

			}

			private void readOutput(String title) {
				try {
					ProcessBuilder builder = new ProcessBuilder("/usr/local/hadoop-2.6.0/bin/hadoop", "fs", "-cat",
							"/user/phamvanvung/airline/output/part-r-00000");
					builder.redirectErrorStream(true);
					Process process = builder.start();
					InputStream is = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					String line = null;
					String output = "";
					output += title + "\n\n";
					while ((line = reader.readLine()) != null) {
						output += line + "\n";
						System.out.println(line);
					}

					textArea.setText(output);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnTask_7 = new JButton("Task 8");
		btnTask_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					task8.Task8Analysis.main(null);
					readOutput("How many angry/happy/etc. tweets for flights? We can perform sentiment analysis on the set of tweets to separate tweets by emotion: angry, happy, etc. To perform sentiment analysis, we can use a free online sentiment database, SentiWordNet (http://sentiwordnet.isti.cnr.it/), to find the emotions most likely associated with each tweet.");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;

			}

			private void readOutput(String title) {
				try {
					ProcessBuilder builder = new ProcessBuilder("/usr/local/hadoop-2.6.0/bin/hadoop", "fs", "-cat",
							"/user/phamvanvung/airline/output/part-r-00000");
					builder.redirectErrorStream(true);
					Process process = builder.start();
					InputStream is = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					String line = null;
					String output = "";
					output += title + "\n\n";
					while ((line = reader.readLine()) != null) {
						output += line + "\n";
						System.out.println(line);
					}

					textArea.setText(output);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnTask_8 = new JButton("Task 9");
		btnTask_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					task9.TaskExtra.main(null);
					readOutput("Top 3 airlines with luggage issues");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;

			}

			private void readOutput(String title) {
				try {
					ProcessBuilder builder = new ProcessBuilder("/usr/local/hadoop-2.6.0/bin/hadoop", "fs", "-cat",
							"/user/phamvanvung/airline/output/part-r-00000");
					builder.redirectErrorStream(true);
					Process process = builder.start();
					InputStream is = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					String line = null;
					String output = "";
					output += title + "\n\n";
					while ((line = reader.readLine()) != null) {
						output += line + "\n";
						System.out.println(line);
					}

					textArea.setText(output);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnTask_9 = new JButton("Task 10");
		btnTask_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					task10.AirlineNegativeSentiment.main(null);
					readOutput("Average trust level of each countries the airline flies");

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;

			}

			private void readOutput(String title) {
				try {
					ProcessBuilder builder = new ProcessBuilder("/usr/local/hadoop-2.6.0/bin/hadoop", "fs", "-cat",
							"/user/phamvanvung/airline/output/part-r-00000");
					builder.redirectErrorStream(true);
					Process process = builder.start();
					InputStream is = process.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					String line = null;
					String output = "";
					output += title + "\n\n";
					while ((line = reader.readLine()) != null) {
						output += line + "\n";
						System.out.println(line);
					}

					textArea.setText(output);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnTask_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTask_4)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTask_5)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTask_6)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTask)
					.addGap(11))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnTask_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnTask_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTask_7)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTask_8)
					.addContainerGap(88, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnTask_9)
					.addContainerGap(311, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnTask_3)
						.addComponent(btnTask_4)
						.addComponent(btnTask_5)
						.addComponent(btnTask_6)
						.addComponent(btnTask))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnTask_1)
						.addComponent(btnTask_2)
						.addComponent(btnTask_7)
						.addComponent(btnTask_8))
					.addGap(5)
					.addComponent(btnTask_9)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		contentPane.setLayout(gl_contentPane);
	}
}
