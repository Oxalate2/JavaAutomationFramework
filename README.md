# CSV Comparison Automation Framework

A robust Java-based automation framework for comparing CSV files and identifying differences with detailed reporting capabilities.

## 🎯 Project Overview

This automation framework provides a comprehensive solution for comparing two CSV files and generating detailed reports of any differences found. The framework is designed with extensibility, maintainability, and clear reporting in mind.

### Key Features

- **Comprehensive CSV Comparison**: Compare two CSV files row-by-row and column-by-column
- **Detailed Difference Reporting**: Clear identification of additions, deletions, and modifications
- **Multiple Output Formats**: Console output, HTML reports, and CSV difference reports
- **Robust Error Handling**: Handles various CSV formats and edge cases
- **Test Data Coverage**: Includes comprehensive test scenarios for validation
- **Easy Configuration**: Simple setup and execution process

## 🚀 Getting Started

### Prerequisites

- Java 8 or higher
- Basic command line knowledge

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Oxalate2/JavaAutomationFramework.git
cd JavaAutomationFramework
```

2. Compile the project:
```bash
javac *.java
```

## 📋 How to Run

### Step 1: Generate Test Data (First Time Setup)

Generate comprehensive test data covering all scenarios:

```bash
java TestDataGenerator
```

This creates 20 CSV files covering various test scenarios in your current directory.

### Step 2: Run Individual CSV Comparisons

Compare any two CSV files directly:

```bash
java CSVComparator file1.csv file2.csv
```

**Examples:**
```bash
# Compare identical files
java CSVComparator test_identical_1.csv test_identical_2.csv

# Compare files with different cell values
java CSVComparator test_different_cells_1.csv test_different_cells_2.csv

# Compare files with structural differences
java CSVComparator test_different_rows_1.csv test_different_rows_2.csv
```

### Step 3: Run Automated Test Suite

Execute all test scenarios automatically:

```bash
java TestRunner
```

This will:
- Generate fresh test data
- Run all 10 test scenarios
- Provide detailed pass/fail results
- Show comprehensive test coverage summary

## 📊 Sample Output

### When Files Are Identical
```
==========================================
CSV COMPARISON REPORT
==========================================
File 1: test_identical_1.csv
File 2: test_identical_2.csv
==========================================
RESULT: FILES ARE IDENTICAL
==========================================
END OF REPORT
==========================================
```

### When Files Are Different
```
==========================================
CSV COMPARISON REPORT
==========================================
File 1: test_different_cells_1.csv
File 2: test_different_cells_2.csv
==========================================
RESULT: FILES ARE NOT IDENTICAL
Total Difference Found: 2

== CELL VALUE DIFFERENCE ==
CELL DIFFERENCE:
 Location: Row 1, Column 1
 File1 Value: 'John'
 File2 Value: 'Jim'

== STRUCTURAL DIFFERENCE ==
STRUCTURAL DIFFERENCE:
 Type: Missing row in file2
 File 1: 4 rows
 File2: 3 rows
==========================================
END OF REPORT
==========================================
```

### Automated Test Suite Output
```
============================================================
TEST 1: Identical files should be detected as identical
============================================================
Comparing: test_identical_1.csv vs test_identical_2.csv
Files identical: true
Differences found: 0
PASSED

============================================================
TEST 2: Different cell values should be detected
============================================================
Comparing: test_different_cells_1.csv vs test_different_cells_2.csv
Files identical: false
Differences found: 1
Actual difference found:
  - Cell Value Difference: Row 1, Column 1: 'John' vs 'Jim'
PASSED

============================================================
TEST SUMMARY
============================================================
Total Tests: 10
Passed: 10
Failed: 0
Success Rate: 100.0%
============================================================
```

## 🧪 Test Data Scenarios

The framework automatically generates comprehensive test data covering 10 distinct scenarios:

### 1. **Identical Files** (`test_identical_1.csv` & `test_identical_2.csv`)
- **Purpose**: Verify framework correctly identifies identical files
- **Data**: Employee records with ID, Name, Age, City
- **Expected**: Files should be reported as identical

### 2. **Different Cell Values** (`test_different_cells_1.csv` & `test_different_cells_2.csv`)
- **Purpose**: Detect individual cell value changes
- **Difference**: "John" vs "Jim" in name field
- **Expected**: Cell Value Difference detected

### 3. **Different Row Counts** (`test_different_rows_1.csv` & `test_different_rows_2.csv`)
- **Purpose**: Identify when files have different number of rows
- **Difference**: File1 has 4 rows, File2 has 6 rows
- **Expected**: Structural Difference and Missing Row differences

### 4. **Different Column Counts** (`test_different_columns_1.csv` & `test_different_columns_2.csv`)
- **Purpose**: Test column structure variations
- **Difference**: Both files have same columns but different formatting
- **Expected**: Structural differences if column counts vary

### 5. **Different Headers** (`test_different_headers_1.csv` & `test_different_headers_2.csv`)
- **Purpose**: Detect header/column name changes
- **Difference**: "Name" vs "Full Name", "City" vs "Location"
- **Expected**: Cell Value Differences in header row

### 6. **Missing Rows in Middle** (`test_missing_middle_1.csv` & `test_missing_middle_2.csv`)
- **Purpose**: Identify missing rows in the middle of files
- **Difference**: File2 missing row 2 (Jane's record)
- **Expected**: Structural and Cell Value differences

### 7. **Extra Columns in Specific Rows** (`test_extra_columns_1.csv` & `test_extra_columns_2.csv`)
- **Purpose**: Handle inconsistent column counts per row
- **Difference**: Row 2 in File2 has extra "EXTRA DATA" column
- **Expected**: Structural Difference

### 8. **Empty Files** (`test_empty_file1.csv` & `test_empty_file2.csv`)
- **Purpose**: Test handling of completely empty files
- **Data**: Both files are empty
- **Expected**: Files should be identical

### 9. **One Empty vs Content** (`test_one_empty_1.csv` & `test_one_empty_2.csv`)
- **Purpose**: Compare empty file with file containing data
- **Difference**: File1 empty, File2 has header and 1 data row
- **Expected**: Structural and Missing Row differences

### 10. **Different Data Types** (`test_different_types_1.csv` & `test_different_types_2.csv`)
- **Purpose**: Test different data format handling
- **Difference**: "10000" vs "50000.00", "30000" vs "N/A"
- **Expected**: Cell Value Differences

## 📁 Project Structure

```
JavaAutomationFramework/
├── CSVComparator.java           # Main comparison engine
├── TestRunner.java              # Automated test suite runner
├── TestDataGenerator.java       # Generates test CSV files
├── ComparisonResult.java        # Result container class
├── ReportGenerator.java         # Report formatting and output
├── Difference.java              # Abstract base class for differences
├── CellDifference.java          # Cell-level difference implementation
├── StructuralDifference.java    # Structural difference implementation
├── MissingRowDifference.java    # Missing row difference implementation
├── README.md                    # This documentation
└── [Generated test files]       # 20 CSV files for testing
    ├── test_identical_1.csv
    ├── test_identical_2.csv
    ├── test_different_cells_1.csv
    ├── test_different_cells_2.csv
    ├── test_different_rows_1.csv
    ├── test_different_rows_2.csv
    ├── test_different_columns_1.csv
    ├── test_different_columns_2.csv
    ├── test_different_headers_1.csv
    ├── test_different_headers_2.csv
    ├── test_missing_middle_1.csv
    ├── test_missing_middle_2.csv
    ├── test_extra_columns_1.csv
    ├── test_extra_columns_2.csv
    ├── test_empty_file1.csv
    ├── test_empty_file2.csv
    ├── test_one_empty_1.csv
    ├── test_one_empty_2.csv
    ├── test_different_types_1.csv
    └── test_different_types_2.csv
```

## 🏗️ Architecture Overview

### Core Components

1. **CSVComparator**: Main comparison engine that reads CSV files and performs row-by-row, cell-by-cell comparison
2. **ComparisonResult**: Container class that holds comparison results and difference collections
3. **ReportGenerator**: Formats and displays comparison results in a user-friendly manner
4. **Difference Hierarchy**: 
   - `Difference` (Abstract base class)
   - `CellDifference` (Cell-level value differences)
   - `StructuralDifference` (File structure differences)
   - `MissingRowDifference` (Missing row differences)

### Key Features

- **Polymorphic Difference Handling**: Different types of differences are handled through inheritance
- **Detailed Reporting**: Each difference type provides specific detailed reports
- **Grouping by Type**: Differences are grouped by type for easy triaging
- **Comprehensive Testing**: Automated test suite validates all scenarios
- **Easy Extension**: New difference types can be added by extending the `Difference` class
