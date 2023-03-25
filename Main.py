import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import dash
import dash_html_components as html
import dash_core_components as dcc

# Read data from Excel sheets
df1 = pd.read_excel('data.xlsx', sheet_name='Sheet1')
df2 = pd.read_excel('data.xlsx', sheet_name='Sheet2')

# Data cleaning and processing
df1 = df1.drop(['Column1'], axis=1)
df2 = df2.dropna()
df2['Metric'] = df2['Column2'] * df2['Column3']

# Create dashboard
app = dash.Dash(__name__)

app.layout = html.Div(children=[
    html.H1(children='Dashboard'),
    html.Div(children='Visualizations and Metrics'),
    
    # Histogram
    dcc.Graph(
        figure={
            'data': [
                {'x': df1['Column2'], 'type': 'histogram'}
            ],
            'layout': {
                'title': 'Histogram of Column2'
            }
        }
    ),
    
    # Line chart
    dcc.Graph(
        figure={
            'data': [
                {'x': df2['Date'], 'y': df2['Metric'], 'type': 'line'}
            ],
            'layout': {
                'title': 'Metric Over Time',
                'xaxis': {'title': 'Date'},
                'yaxis': {'title': 'Metric'}
            }
        }
    )
])

if __name__ == '__main__':
    app.run_server(debug=True)
