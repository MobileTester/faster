package com.application.utils.createpojo;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class PojoCreationForWeb {

	// package where the Bean files will be generated
	private String packageName = "com.application.testsuite.testdata.web.beans";
	// source path for the Bean
	private String sourcePath = "." + File.separator + "src/test/resources/testdata/";
	// output path for the generated Bean
	private String outputPojoDirectoryPath = "." + File.separator + "src/test/java";

	public static void main(String[] args) {
		// Enter input file for the json template here
		String inputJsonFileName = "TestCase01Testdata.json";
		// Will create the POJO files in the mentioned package - packageName 
		PojoCreationForWeb obj = new PojoCreationForWeb();
		obj.getInputAndConvert2JSON(obj.packageName, inputJsonFileName, obj.outputPojoDirectoryPath);
	}

	// Get the input file and convert to JSON
	public void getInputAndConvert2JSON(String packageName, String inputJsonFileName, String outputPojoDirectoryPath) {

		File inputJson = new File(sourcePath + inputJsonFileName);
		File outputPojoDirectory = new File(outputPojoDirectoryPath);

		outputPojoDirectory.mkdirs();
		try {
			
			convert2JSON(inputJson.toURI().toURL(), outputPojoDirectory, packageName, inputJson.getName().replace(".json", ""));
		} 
		catch (IOException e) {
			System.out.println("Encountered issue while converting to pojo: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Convert to JSON
	public void convert2JSON(URL inputJson, File outputPojoDirectory, String packageName, String className)	throws IOException {
		
		JCodeModel codeModel = new JCodeModel();
		URL source = inputJson;
		GenerationConfig config = new DefaultGenerationConfig() {
			@Override
			public boolean isGenerateBuilders() { // set config option by
													// overriding method
				return true;
			}

			public SourceType getSourceType() {
				return SourceType.JSON;
			}
		};
		SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
		mapper.generate(codeModel, className, packageName, source);
		codeModel.build(outputPojoDirectory);
	}

}
