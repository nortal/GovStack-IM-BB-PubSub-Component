const express = require('express');
const bodyParser = require('body-parser');
const swaggerUi = require('swagger-ui-express');
const swaggerJsDoc = require('swagger-jsdoc');

const app = express();
const port = 4000;

app.use(bodyParser.json());

const swaggerOptions = {
    swaggerDefinition: {
        openapi: '3.0.0',
        info: {
            title: 'Subscriber API',
            version: '1.0.0',
            description: 'API documentation for a subscriber service'
        },
        servers: [
            {
                url: `http://localhost:${port}`
            }
        ],
        tags: [
            {
                name: 'Events',
                description: 'Endpoints related to handling events'
            },
            {
                name: 'Health',
                description: 'Endpoints for health checks'
            }
        ]
    },
    apis: ['index.js']
};

const swaggerSpec = swaggerJsDoc(swaggerOptions);

app.use('/swagger-ui', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

app.get('/v3/api-docs', (req, res) => {
    res.setHeader('Content-Type', 'application/json');
    res.send(swaggerSpec);
});

/**
 * @swagger
 * /api/health:
 *   get:
 *     summary: Health check endpoint
 *     tags: [Health]
 *     responses:
 *       200:
 *         description: Server is healthy
 */
app.get('/api/health', (req, res) => {
    res.json({status: 'ok'});
});

/**
 * @swagger
 * /api/{subscriber}/callback:
 *   post:
 *     summary: A callback endpoint to receive and log events
 *     tags: [Events]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *            type: object
 *     parameters:
 *       - in: path
 *         name: subscriber
 *         required: true
 *         schema:
 *           type: string
 *         description: name of subscriber
 *     responses:
 *       200:
 *         description: Event received successfully
 */
app.post('/api/:subscriber/callback', (req, res) => {
    const date = new Date();
    const subscriber = req.params.subscriber;
    const data = req.body;
    console.log(`${date.toISOString()} Subscriber ${subscriber} received data:`, data);
    res.json({message: 'Event received successfully'});
});

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
