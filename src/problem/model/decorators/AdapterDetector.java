package problem.model.decorators;

import problem.models.api.IModel;

public class AdapterDetector implements IPatternDetector {
	private IModel model;
	
	public AdapterDetector(IModel model) {
		this.model = model;
	}
	@Override
	public void detectPatterns() {
		// TODO Auto-generated method stub

	}

}
