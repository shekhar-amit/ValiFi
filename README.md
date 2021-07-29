
# ValiFi

* __ValiFi__ is an ohos library for validating fields or whole forms. 
* The validations are visible immediately when the user adds input. 
* It's highly customizable and simple to use.

# Source
The library was inspired from the Android Library: [ValiFi](https://github.com/mlykotom/valifi/) (version 1.5.0)

## Features
The library supports rapid and customizable form validation such that the validations are visible immediately as the user adds input. It supports validation for Numeric, Phone Number, Email, Regex, Date, Credit Card, and Minimum/Maximum/Range character length. The validators can also be customized as required.

  
## Dependency
1. For using ValiFi module in sample app, include the source code and add the below dependencies in entry/build.gradle to generate hap/support.har.
```
	dependencies {
		implementation project(':valifi')
		testCompile 'junit:junit:4.12'
	}
```
2. For using ValiFi module in separate application using har file, add the har file in the entry/libs folder and add the dependencies in entry/build.gradle file.
```
	dependencies {
		implementation fileTree(dir: 'libs', include: ['*.har'])
		testCompile 'junit:junit:4.12'
	}

```
3. For using ValiFi from a remote repository in separate application, add the below dependencies in entry/build.gradle file.
```
	dependencies {
		implementation 'dev.applibgroup:valifi:1.0.0'
        	testCompile 'junit:junit:4.12'
	}

```

## Usage


#### 1. Create field you want to validate
```java
public final ValiFieldEmail email = new ValiFieldEmail();
```

#### 2. Initialize via Java

For initialization of a single validator:
```java
public final ValiFieldEmail email = new ValiFieldEmail();
email.setTextField(textfield_email);
email.setErrorText(errortext_email);
email.init()

if(email.isValid())
	// do something
```

For multiple validators use a form:

```java
public final ValiFieldEmail email = new ValiFieldEmail();
public final ValiFieldPassword password = new ValiFieldPassword();
public final ValiFiForm form = new ValiFiForm(email, password, phone, ...);

email.setTextField(textfield_email);
email.setErrorText(errortext_email);
password.setTextField(textfield_password);
password.setErrorText(errortext_password);

form.init()

if(form.isValid())
	// do something
```

When a user types their e-mail, it will automatically validate the input entered in the TextField `textfield_email` and show the error in Text `errortext_email`. The `init()` method needs to be called after setting the corresponding error text and text field for live validator to initialize. 

The `isValid()` can be used to check explicitly for validity, say when clicking on submit button. 

## Pre-Defined Validators

### Base
-   addVerifyFieldValidator
-   addCustomValidator

### ValiFieldText
-   addExactLengthValidator
-   addMaxLengthValidator
-   addMinLengthValidator
-   addNotEmptyValidator
-   addPaternValidator
-   addRangeLengthValidator

## Customization

### Global Resource Customization

```java
public class MyApplication extends AbilityPackage{
    @Override
    public void onInitialize() {
	    super.onInitialize();
		ValiFi.install(getContext(), 
			new ValiFi.Builder()
				.setErrorResource(ValiFi.Builder.ERROR_RES_EMAIL, R.string.my_custom_email_error)
				.setPattern(ValiFi.Builder.PATTERN_EMAIL, Patterns.EMAIL_ADDRESS)
				.build()
		);
    }
}
```

### Custom Validators

Custom validation can be achieved by using `addCustomValidator()` to override the `isValid()` function.

```java
public final ValiFieldText fieldWithDifferentValidations = new ValiFieldText();

fieldWithDifferentValidations
	.addRangeLengthValidator(3, 10)
	.setEmptyAllowed(true)
	.addCustomValidator("custom not valid", new ValiFieldBase.PropertyValidator<String>() {
		@Override
		public boolean isValid(@Nullable String value) {
			return whenThisIsValid;
		}
	});
```
### Global Custom Validation

If you want to use your custom validation across all application, just inherit base field and add your app logic.

```java
public class MyValiFieldCaptcha extends ValiFieldText {
	public MyValiFieldCaptcha() {
		super();
		addMyValidator();
	}


	public MyValiFieldCaptcha(String defaultValue) {
		super(defaultValue);
		addMyValidator();
	}


	private void addMyValidator() {
		addCustomValidator("Captcha must be correct", new PropertyValidator<String>() {
			@Override
			public boolean isValid(@Nullable String value) {
				// custom validation rule
				return "captcha".equals(value);
			}
		});
	}
}
```
  
  ### Credit Cards
For validating credit cards numbers, one can use class  `ValiFieldCard`.

It will be checking if input:

-   is parseable number
-   passes Luhn algorithm test
-   is among known card types

Known types are defaultly set as:

-   MASTERCARD
-   AMERICAN_EXPRESS
-   VISA
-   DISCOVER
-   DINERS_CLUB
-   JCB

It is possible to override the settings when installing the library:

```java
ValiFi.install(this, new ValiFi.Builder()
	.setKnownCardTypes(new ValiFiCardType("My custom card", "REGEX_FOR_VALIDATION"))
	.build()
);
```

### Numeric Validation

For number validation, one can use abstract class  `ValiFieldNumber<Type>`  which will be forced to implement  `parse(String value)`  method for getting number from string.

Already implemented classes are Long, Integer, and Double. This field adds possibility of adding number validators:

```java
final long requiredMinNumber = 13;
numLong.addNumberValidator("This number must be greater than 13", new ValiFieldNumber.NumberValidator<Long>() {
	@Override
	public boolean isValid(@NonNull Long value) {
		return value > requiredMinNumber;
	}
});
```

And if input is not parseable then it will set as invalid without the need of specifying the parser.

## Sample

A sample app with some use cases of the library is available on this [link](entry/) .

## Future work

Future work would include implementing similar live validation functionality via data binding.

# License
    Copyright 2018 Tomas Mlynaric

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.