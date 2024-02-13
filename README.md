Small PoC of Selenium with JUnit interacting with SpreadJS instance in an Angular application

### Precondition
App under test must expose SpreadJS module to window context so that it can be visible by automation execution.
```Javascript
import * as GC  from '@mescius/spread-sheets'
window["GC"] = GC
```
### Prerequisites:
Option 1 (default): Clone, install and run demo app: https://github.com/sjimenezqac/AngularSpreadJSSample  
Option 2: Use SpreadJS provided demo app (not an Angular app):  
`driver.get("https://developer.mescius.com/spreadjs/demos/sample/features/worksheet/initialize-sheet/purejs/")`

### Steps
1. Clone repository
2. run `mvn test` after dependencies are loaded

<br/>  

**NOTE:** Sleeps have been intentionally added for demo purposes. 