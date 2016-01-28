package problem.model.decorators;

import problem.models.api.IModel;

public class DecoratorDetector implements IPatternDetector {
	private IModel model;
	
	public DecoratorDetector(IModel model) {
		this.model = model;
	}

	@Override
	public void detectPatterns() {

	}

}
